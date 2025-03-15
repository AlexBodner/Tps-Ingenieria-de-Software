module Route ( Route, newR, inOrderR )
  where
import Data.List (elemIndex)
data Route = Rou [ String ] deriving (Eq, Show)

newR :: [ String ] -> Route                    -- construye una ruta segun una lista de ciudades

newR routeList = Rou routeList

inOrderR :: Route -> String -> String -> Bool  -- indica si la primer ciudad consultada esta antes que la segunda ciudad en la ruta
inOrderR (Rou cities) city1 city2 | ind1 /= Nothing && ind2 /= Nothing = ind1 <= ind2
                           | otherwise = False -- tirar error si no estan los indices
                    where  
                        ind1 = elemIndex city1 cities 
                        ind2 = elemIndex city2 cities

-- Tests: 
-- ghci> r = Rou ["Baires", "Cordoba"]
-- ghci> inOrderR r "Cordoba" "Baires"
-- False
-- ghci> inOrderR r  "Baires" "Cordoba"
-- True