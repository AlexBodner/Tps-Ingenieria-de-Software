module Palet ( Palet, newP, destinationP, netP )
  where

data Palet = Pal String Int deriving (Eq, Show)

newP :: String -> Int -> Palet   -- construye un Palet dada una ciudad de destino y un peso en toneladas
destinationP :: Palet -> String  -- responde la ciudad destino del palet
netP :: Palet -> Int             -- responde el peso en toneladas del palet

------------------------
module Route ( Route, newR, inOrderR )
  where

data Route = Rou [ String ] deriving (Eq, Show)

newR :: [ String ] -> Route                    -- construye una ruta segun una lista de ciudades
inOrderR :: Route -> String -> String -> Bool  -- indica si la primer ciudad consultada esta antes que la segunda ciudad en la ruta

------------------------
module Stack ( Stack, newS, freeCellsS, stackS, netS, holdsS, popS )
  where

import Palet
import Route

data Stack = Sta [ Palet ] Int deriving (Eq, Show)

newS :: Int -> Stack                      -- construye una Pila con la capacidad indicada 
freeCellsS :: Stack -> Int                -- responde la celdas disponibles en la pila
stackS :: Stack -> Palet -> Stack         -- apila el palet indicado en la pila
netS :: Stack -> Int                      -- responde el peso neto de los paletes en la pila
holdsS :: Stack -> Palet -> Route -> Bool -- indica si la pila puede aceptar el palet considerando las ciudades en la ruta
popS :: Stack -> String -> Stack          -- quita del tope los paletes con destino en la ciudad indicada

------------------------
module Truck ( Truck, newT, freeCellsT, loadT, unloadT, netT )
  where

import Palet
import Stack
import Route

data Truck = Tru [ Stack ] Route deriving (Eq, Show)

newT :: Int -> Int -> Route -> Truck  -- construye un camion según una cantidad de bahias, la altura de las mismas y una ruta
freeCellsT :: Truck -> Int            -- responde la celdas disponibles en el camion
loadT :: Truck -> Palet -> Truck      -- carga un palet en el camion
unloadT :: Truck -> String -> Truck   -- responde un camion al que se le han descargado los paletes que podían descargarse en la ciudad
netT :: Truck -> Int                  -- responde el peso neto en toneladas de los paletes en el camion