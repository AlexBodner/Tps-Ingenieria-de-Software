module Route ( Route, newR, inOrderR, inRouteR )
  where
import Data.List (elemIndex)
data Route = Rou [ String ] deriving (Eq, Show)

newR :: [ String ] -> Route                    -- construye una ruta segun una lista de ciudades

newR routeList = Rou routeList

inOrderR :: Route -> String -> String -> Bool  -- indica si la primer ciudad consultada esta antes que la segunda ciudad en la ruta. si alguna no esta lanza error naturalmente.
inOrderR (Rou cities) city1 city2 | ind1 /= Nothing && ind2 /= Nothing = ind1 <= ind2
                                  | ind1 == Nothing && ind2==Nothing  =error "Error al ingresar las ciudades, ninguna se encuentra en la ruta."                         
                                  | ind1 == Nothing = error "Error al ingresar las ciudades, la ciudad 1 no se encuentra en la ruta."
                                  | otherwise =  error "Error al ingresar las ciudades, la ciudad 2 no se encuentra en la ruta."  

                    where  
                        ind1 = elemIndex city1 cities 
                        ind2 = elemIndex city2 cities

inRouteR :: Route -> String -> Bool -- indica si la ciudad consultada esta en la ruta
inRouteR (Rou cities) city = elem city cities -- elem verifica si un elemento está dentro de una lista.