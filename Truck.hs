module Truck ( Truck, newT, freeCellsT, loadT, unloadT, netT )
  where

import Palet
import Stack
import Route

data Truck = Tru [ Stack ] Route deriving (Eq, Show)

newT :: Int -> Int -> Route -> Truck  -- construye un camion según una cantidad de bahias, la altura de las mismas y una ruta
newT bahias altura ruta = (Tru (replicate bahias (createStack altura)) ruta)
-- Crea una lista de 'bahias' (stacks), cada uno con altura 'altura'


freeCellsT :: Truck -> Int            -- responde la celdas disponibles en el camion
freeCellsT (Tru stack_list _) = sum [freeCellsS n | n <- stack_list] -- a cada stack de los stacks del camion le aplica la funcion freeCellsS, y suma todo


loadT :: Truck -> Palet -> Truck   -- carga un palet en el camion
loadT (Tru stack_list ruta) palet = Tru (loadIntoFirstAvailable (Tru stack_list ruta) palet) ruta

-- Funcion recursiva que descompone la lista en 2: el primer elemento y el resto de la lista. Y se va llamando recursivamente hasta descomponerla toda o encontrar un hueco libre.
loadIntoFirstAvailable :: Truck -> Palet -> [Stack]
loadIntoFirstAvailable (Tru [] _ ) palet = []  -- Si la lista está vacía, no hay stacks donde cargar el palet
loadIntoFirstAvailable ( Tru (primerStack:restoStacks) ruta) palet -- Si la lista no está vacía, descompongo en primerStack y restoStacks
  | freeCellsS primerStack > 0 && holdsS primerStack palet ruta = (stackS primerStack palet):restoStacks -- stackS apila el palet en el primer stack, con : se agrega el resto de los stacks
  | otherwise = primerStack : loadIntoFirstAvailable (Tru restoStacks ruta) palet -- Si no se puede apilar en el 1er stack, se separa ese stack y se llama recursivamente con el resto de los stacks
  -- eventualmente si no se puede con ningun stack, la variable restoStacks va a ser [] y se termina la recursion


unloadT :: Truck -> String -> Truck   -- responde un camion al que se le han descargado los paletes que podían descargarse en la ciudad
unloadT (Tru stack_list ruta)  city = Tru([popS n  city| n <- stack_list ]) ruta

netT :: Truck -> Int                  -- responde el peso neto en toneladas de los paletes en el camion
netT  (Tru stack_list ruta) = sum [netS s| s <- stack_list ] 