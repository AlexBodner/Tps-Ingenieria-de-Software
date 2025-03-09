module Palet ( Palet, newP, destinationP, netP )
  where


-- Declara un tipo de dato Palet, con constructor Pal que toma un String y un Int. Deriving Eq lo hace comparable usando == y Show lo hace printeable.
data Palet = Pal String Int deriving (Eq, Show)


-- Constructor function

newP :: String -> Int -> Palet   -- construye un Palet dada una ciudad de destino y un peso en toneladas

newP city weight = Pal city weight -- NewP llama al constructor con los parametros


-- Extract destination
destinationP :: Palet -> String  -- responde la ciudad destino del palet
destinationP (Pal city _) = city -- Extrae el primer valor del constructor Pal

-- Extract weight
netP :: Palet -> Int  -- responde el peso en toneladas del palet
netP (Pal _ weight) = weight -- Extrae el segundo valor del constructor Pal


-- Para probarlo en ghci:
-- Crear palet: p = newP "Buenos Aires" 10
-- printear peso: netP(p)