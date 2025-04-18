import Truck
import Palet
import Route
import Stack
import TestExceptions (testF)

-- Crear palets
palet1 = newP "BuenosAires" 3
palet2 = newP "Rosario" 2
palet3 = newP "Cordoba" 5
palet4 = newP "Cordoba" 2
palet5 = newP "Chubut" 2
palet6  = newP "Mendoza" 4
palet7 = newP "Salta" 4

ruta = newR ["BuenosAires", "Rosario", "Cordoba"]

rutaLarga = newR ["BuenosAires", "Rosario", "Cordoba","Mendoza","Salta","Chubut"]

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


  -- Test 6: Crear otro camión
  let truck2 = newT 3 2 rutaLarga  -- Camión con 3 bahías, cada una con altura 2

  assert (freeCellsT truck2 == 6) "newT crea un camión con 3 bahías, cada una con altura 2 vacías"

  -- Test 7: Llenamos el camion de palets
  let truck_a = loadT truck2 palet1 -- Baires

  assert (freeCellsT truck_a == 5) "Load T carga bien con 3 bahias de altura 2"
  let truck_b = loadT truck_a palet1
  assert (freeCellsT truck_b == 4) "Load T carga bien con 3 bahias de altura 2"

  let truck_c = loadT truck_b palet4--Cordoba
  assert (freeCellsT truck_c == 3) "Load T carga bien con 3 bahias de altura 2"

  let truck_d = loadT truck_c palet4
  assert (freeCellsT truck_d == 2) "Load T carga bien con 3 bahias de altura 2"

  let truck_e = loadT truck_d palet5--Chubut
  assert (freeCellsT truck_e == 1) "Load T carga bien con 3 bahias de altura 2"

  let truck_f = loadT truck_e palet5

  assert (freeCellsT truck_f == 0) "loadT reduce las celdas disponibles a 0 tras llenar de palet"
  
  assert ((freeCellsT (loadT truck_f palet5) == freeCellsT truck_f) && (netT (loadT truck_f palet5) == netT truck_f)) "loadT deja el mismo camion si está lleno" 

  -- Test 8: Descargar palets
  let truck_g = unloadT truck_f "BuenosAires"
  assert (netT truck_g == 8) "Unload descargó bien el peso"
  assert (freeCellsT truck_g == 2) "Unload descargó bien el palet."


  -- Test 9: Cargar palets que no pueden ser por el orden

  let truck_h = loadT truck_g palet6 -- al cargar palet 6, solo me queda un espacio en esa bahia, entonces no podria cargar algo que vaya mas lejos que mendoza

  assert (freeCellsT (loadT truck_h palet7) == (freeCellsT  truck_h)) "No carga Palet que no respeta orden"


  -- Test 10: Crear camion con altura 0 falla (efectivamente se lanza un error, que por algun motivo testF no captura, por lo que lo dejamos comentado)
  --  print((newT 2 0 ruta)) 

  -- Test: camion de una sola bahia
  let truck = newT 1 4 ruta  -- Camión con 1 bahías, cada una con altura 4
  assert (freeCellsT truck == 4) "Se crea correctamente camion de 1 bahia. Las celdas vacias son las correctas"
  assert (netT truck == 0) "Se crea correctamente camion de 1 bahia. El peso es 0."

  -- Test 2: Cargar palets en orden correcto
  let truck1 = loadT truck palet3 --Cordoba
  assert (freeCellsT truck1 == 3) "loadT reduce las celdas disponibles tras cargar un palet"
  assert (netT truck1 == 5 ) "netT calcula correctamente el peso total con 1 elemento."

  let truck2 = loadT truck1 palet2
  assert (freeCellsT truck2 == 2) "loadT carga el destino anterior correctamente en la misma pila."

  assert (netT truck2 == 7) "netT calcula correctamente el peso total."


  let truck3 = loadT truck2 palet1
  assert (freeCellsT truck3 == 1 && (netT truck3 == 10)) "Se cargaron correctamente los palets en el orden correcto."
  assert (netT(loadT truck3 palet1) == 10) "Se queda el mismo peso cuando el peso de la bahia es 10."
  assert (freeCellsT(loadT truck3 palet1) == 1) "No se carga el palet cuando el peso de la bahia es 10."


  putStrLn "\n✅ Todos los tests para Truck finalizados."
