import Truck
import Palet
import Route
import Stack

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
  print (freeCellsT truck_a )

  let truck_b = loadT truck_a palet1
  print (freeCellsT truck_b )

  let truck_c = loadT truck_b palet4--Cordoba
  print (freeCellsT truck_c )

  let truck_d = loadT truck_c palet4
  print (freeCellsT truck_d )

  let truck_e = loadT truck_d palet5--Chubut
  print (freeCellsT truck_e )

  let truck_f = loadT truck_e palet5

  print (freeCellsT truck_f )
  assert (freeCellsT truck_f == 0) "loadT reduce las celdas disponibles a 0 tras llenar de palet"
  
  assert ((loadT truck_f palet5) == truck_f) "loadT deja el mismo camion si está lleno"

  -- Test 8: Descargar palets
  let truck_g = unloadT truck_f "BuenosAires"
  print (netT truck_g)
  assert (netT truck_g == 8) "Unload descargó bien el peso"


  -- Test 9: Cargar palets que no pueden ser por el orden

  let truck_h = loadT truck_g palet6 -- al cargar palet 6, solo me queda un espacio en esa bahia, entonces no podria cargar algo que vaya mas lejos que mendoza

  assert ((loadT truck_h palet7) == truck_h) "No carga Palet que no respeta orden"



  putStrLn "\n✅ Todos los tests para Truck finalizados."
