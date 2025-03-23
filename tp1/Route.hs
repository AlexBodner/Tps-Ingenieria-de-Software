module Route ( Route, newR, inOrderR, inRouteR )
  where
import Data.List (elemIndex)
data Route = Rou [ String ] deriving (Eq, Show)

newR :: [ String ] -> Route                    -- construye una ruta segun una lista de ciudades

newR routeList = Rou routeList

inOrderR :: Route -> String -> String -> Bool  -- indica si la primer ciudad consultada esta antes que la segunda ciudad en la ruta. 
inOrderR (Rou cities) city1 city2 | ind1 <= ind2 = True
                                  | otherwise = False
                    where  
                        ind1 = elemIndex city1 cities 
                        ind2 = elemIndex city2 cities

inRouteR :: Route -> String -> Bool -- indica si la ciudad consultada esta en la ruta
inRouteR (Rou cities) city = elem city cities -- elem verifica si un elemento está dentro de una lista.