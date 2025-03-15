loadIntoFirstAvailable :: [Stack] -> Palet -> [Stack]
--Funcion recursiva que descompone la lista en 2: el primer elemento y el resto de la lista. Y se va llamando recursivamente hasta descomponerla toda o encontrar un hueco libre. 
loadIntoFirstAvailable [] _ = []  -- 
loadIntoFirstAvailable (primero:resto) palet
  | freeCellsS primero > 0 && holdsS primero palet  = stackS primero palet : resto
  | otherwise       = (primero : loadIntoFirstAvailable resto )palet