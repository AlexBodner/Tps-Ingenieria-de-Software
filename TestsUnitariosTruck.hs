import Truck
import Palet
import Route
import Stack

-- Crear palets
palet1 = newP "BuenosAires" 3
palet2 = newP "Rosario" 2
palet3 = newP "Cordoba" 5

ruta = newR ["BuenosAires", "Rosario", "Cordoba"]

-- Crear función de testeo
assert :: Bool -> String -> IO ()
assert cond msg = putStrLn $ if cond then "✅ OK: " ++ msg else "❌ Falló: " ++ msg

main :: IO ()
main = do
  let truck = newT 2 3 ruta  -- Camión con 2 bahías, cada una con altura 3

  -- Test 1: Crear camión
  assert (freeCellsT truck == 6) "newT crea un camión con 2 bahías de altura 3 vacías"

  -- Test 2: Cargar un palet
  let truck1 = loadT truck palet1
  assert (freeCellsT truck1 == 5) "loadT reduce las celdas disponibles tras cargar un palet"

  -- Test 3: Cargar más palets
  let truck2 = loadT truck1 palet2
  assert (freeCellsT truck2 == 4) "loadT sigue funcionando con múltiples palets"

  -- Test 4: Peso neto del camión
  assert (netT truck2 == 5) "netT calcula correctamente el peso total"

  -- Test 5: Descarga en una ciudad
  let truck3 = unloadT truck2 "BuenosAires"
  assert (freeCellsT truck3 == 5) "unloadT descarga correctamente en la ciudad"

  putStrLn "\n✅ Todos los tests para Truck finalizados."
