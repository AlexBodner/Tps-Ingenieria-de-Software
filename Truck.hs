module Truck ( Truck, newT, freeCellsT, loadT, unloadT, netT )
  where

import Palet
import Stack
import Route

data Truck = Tru [ Stack ] Route deriving (Eq, Show)

newT :: Int -> Int -> Route -> Truck  -- construye un camion según una cantidad de bahias, la altura de las mismas y una ruta
newT bahias altura ruta = Tru (replicate bahias (createStack altura)) ruta 


freeCellsT :: Truck -> Int            -- responde la celdas disponibles en el camion
freeCellsT (Tru stack_list _) = sum [freeCellsS n | n <- stack_list , True]


loadT :: Truck -> Palet -> Truck   -- carga un palet en el camion
loadT Tru(stack_list ruta) palet  = Tru (loadIntoFirstAvailable stack_list  palet)


loadIntoFirstAvailable [] _ = [] --Funcion recursiva que descompone la lista en 2: el primer elemento y el resto de la lista. Y se va llamando recursivamente hasta descomponerla toda o encontrar un hueco libre.  
loadIntoFirstAvailable (primero:resto) palet
  | freeCellsS primero > 0 && holdsS primero palet  = (stackS primer palet) : resto
  | otherwise       = (primero : loadIntoFirstAvailable resto )palet


unloadT :: Truck -> String -> Truck   -- responde un camion al que se le han descargado los paletes que podían descargarse en la ciudad
unloadT Tru (stack_list ruta)  city = Tru([popS n  city| n <- stack_list ] ruta) 

netT :: Truck -> Int                  -- responde el peso neto en toneladas de los paletes en el camion
net  Tru (stack_list ruta) = sum [netS s| s <- stack_list ] 