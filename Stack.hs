module Stack ( Stack, newS, freeCellsS, stackS, netS, holdsS, popS )
  where

import Palet
import Route

data Stack = Sta [ Palet ] Int deriving (Eq, Show) -- Stack es un tipo de dato. Sta es el constructor que recive una
-- lista de Palets y un Int que debe soportar Eq y Show.

newS :: Int -> Stack                      -- construye una Pila con la capacidad indicada 
newS cap = Sta [] cap
                  
freeCellsS :: Stack -> Int                -- responde la celdas disponibles en la pila
freeCellsS (Sta palets cap) = cap - length palets

stackS :: Stack -> Palet -> Stack         -- apila el palet indicado en la pila
stackS (Sta palets cap) palet | length palets < cap && netS (Sta palets cap) + netP palet <= 10 = Sta (palet:palets) cap
                              | otherwise = error "No hay lugar en la pila"

netS :: Stack -> Int                      -- responde el peso neto de los paletes en la pila
netS (Sta palets _) = sum (map netP palets) -- map aplica netP a cada uno de los elementos de la lista 'palets'

holdsS :: Stack -> Palet -> Route -> Bool -- indica si la pila puede aceptar el palet considerando las ciudades en la ruta
holdsS (Sta [] _) _ _ = True  -- Si la pila está vacía, acepta cualquier palet
holdsS (Sta palets cap) palet ruta =
  length palets < cap  -- Verifica que haya espacio
  && netS (Sta palets cap) + netP palet <= 10  -- Verifica que no supere las 10 toneladas
  && inOrderR ruta (destinationP palet) (destinationP (head palets))  -- Quiero que el palet que voy a agregar se baje antes que el head. 

popS :: Stack -> String -> Stack          -- quita del tope los paletes con destino en la ciudad indicada
-- Entrada => Stack: La pila de palets actual, String: La ciudad donde se están descargando palets.
-- Salida => Stack: La pila de palets actualizada.
popS (Sta palets cap) ciudad 
                    | destinationP (head palets) == ciudad = Sta (dropWhile (\p -> destinationP p == ciudad) palets) cap
                    | otherwise = error "No se pueden desapilar Palets en esta ciudad"

-- Se usa pattern matching para desestructurar el Stack. palets: Lista de palets apilados en la bahía. cap: Capacidad máxima de la bahía.
-- No chequeamos que no se desapile uno que esta abajo de otro que no debe desapilarse piorque hacemos el chequeo
-- de nunca agregar un pallet a la pila cuya ciudad destino viene despues del de un pallet inferior. Al hacer ese chequeo
-- nos aseguramos que no vamos a tener problemas al momento de desapilar. 