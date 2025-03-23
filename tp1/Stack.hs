module Stack ( Stack, newS, freeCellsS, stackS, netS, holdsS, popS )
  where

import Palet
import Route

data Stack = Sta [ Palet ] Int deriving (Eq, Show) -- Stack es un tipo de dato. Sta es el constructor que recive una
-- lista de Palets y un Int que debe soportar Eq y Show.

newS :: Int -> Stack                      -- construye una Pila con la capacidad de palets indicada 
newS cap    | cap <=0 = error "Un Stack no puede tener capacidad menor o igual a 0."
            | otherwise = Sta [] cap
                  
freeCellsS :: Stack -> Int                -- responde la celdas disponibles en la pila
freeCellsS (Sta palets cap) = cap - length palets

stackS :: Stack -> Palet -> Stack         -- apila el palet indicado en la pila
stackS (Sta palets cap) palet | freeCellsS (Sta palets cap)>0 && netS (Sta palets cap) + netP palet <= 10 = Sta (palet:palets) cap
                              | otherwise = error "No hay lugar en la pila"

netS :: Stack -> Int                      -- responde el peso neto de los paletes en la pila
netS (Sta [] _) = 0  -- Si la pila está vacía, el peso neto es 0
netS (Sta palets _) = sum (map netP palets) -- map aplica netP a cada uno de los elementos de la lista 'palets'

--holdS no chequea si esta en la ruta el palet de input, de esto se ocupa el camion, por lo que se asume que holdS solo recibe palets en la ruta. 
holdsS :: Stack -> Palet -> Route -> Bool -- indica si la pila puede aceptar el palet considerando las ciudades en la ruta
holdsS (Sta [] _) pal route = True  -- Si la pila está vacía, acepta cualquier palet 
holdsS (Sta palets cap) palet ruta =
  freeCellsS (Sta palets cap)>0  -- Verifica que haya espacio
  && netS (Sta palets cap) + netP palet <= 10  -- Verifica que no supere las 10 toneladas
  && inOrderR ruta (destinationP palet) (destinationP (head palets))  -- Quiero que el palet que voy a agregar se baje antes que el head. 


popS :: Stack -> String -> Stack          -- quita del tope los paletes con destino en la ciudad indicada
-- Entrada => Stack: La pila de palets actual, String: La ciudad donde se están descargando palets.
-- Salida => Stack: La pila de palets actualizada.
popS (Sta [] cap) _ = Sta [] cap  -- Caso base: si la pila está vacía, no hacemos nada
popS (Sta palets cap) ciudad 
                    | destinationP (head palets) == ciudad = Sta (dropWhile (\p -> destinationP p == ciudad) palets) cap
                    | otherwise = Sta palets cap -- Si el palet no tiene la ciudad destino igual a la ciudad donde se está descargando, no se desapila, devuelve el stack tal cual le llego.
                    -- | otherwise = error "No se pueden desapilar Palets en esta ciudad"    ==> Lo comento porque sino en Truck tira error. El Truck
                    -- chequea stack por stack. Si un stack no tiene nada para descargar en esa ciudad no quiero que tire error y se corte el programa.

-- Se usa pattern matching para desestructurar el Stack. palets: Lista de palets apilados en la bahía. cap: Capacidad máxima de la bahía.
-- No chequeamos que no se desapile uno que esta abajo de otro que no debe desapilarse piorque hacemos el chequeo
-- de nunca agregar un pallet a la pila cuya ciudad destino viene despues del de un pallet inferior. Al hacer ese chequeo
-- nos aseguramos que no vamos a tener problemas al momento de desapilar. 