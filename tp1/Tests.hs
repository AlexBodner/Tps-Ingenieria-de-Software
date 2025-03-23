import TestExceptions (testF)
import Palet
import Route
import Stack
import Truck

-- Crear función de testeo
assert :: Bool -> String -> IO ()
assert cond msg = putStrLn $ if cond then "✅ OK: " ++ msg else "❌ Falló: " ++ msg
-- Palets de prueba
palet1 = newP "BuenosAires" 3 
palet2 = newP  "Rosario" 2 
palet3 = newP  "Cordoba" 5 
palet4 = newP  "Mendoza" 1
palet5 = newP  "Rosario" 11
palet6 = newP  "Cordoba" 8
palet7 = newP  "Chubut" 1
-- Ruta de prueba

rutaStack = newR ["BuenosAires", "Rosario", "Cordoba", "Mendoza"]
rutaCorta = newR ["BuenosAires", "Rosario", "Cordoba"]

rutaLarga = newR ["BuenosAires", "Rosario", "Cordoba","Mendoza","Salta","Chubut"]

main :: IO ()
main = do

  -- Test de Palet
  print("Inician Test de Palet")

  let p = newP "Buenos Aires" 10
  assert(netP(p) == 10) "El peso es el correcto"
  assert(destinationP(p) == "Buenos Aires") "El destino es el correcto"
  assert(testF(newP  "Buenos Aires" (-10))) "Tirar error cuando el peso de Palet es negativo"
  print("Terminados Test de Palet")


  -- Test de Route

  print("Inician Test de Route")

  -- Test: 2 ciudades
  let r1 = newR ["Baires", "Cordoba"] 
  assert(not (inOrderR r1 "Cordoba" "Baires")) "Encuentra que la condicion es Falsa con 2 ciudades."
  assert( inOrderR r1  "Baires" "Cordoba") "Encuentra que la condicion es Verdadera con 2 ciudades."

  assert(inRouteR r1 "Baires") "inRouteR encuentra la primera ciudad"
  assert(inRouteR r1 "Cordoba") "inRouteR encuentra la segunda ciudad"
  assert(not (inRouteR r1 "New York")) "inRouteR devuelve Falso con una ciudad que no está en la ruta de 2 ciudades."

  -- Test 3 ciudades
  let r2 = newR ["Baires", "Rosario","Cordoba"] 
  assert(not (inOrderR r2 "Cordoba" "Baires")) "Encuentra que la condicion es Falsa con 3 ciudades. Compara 2 contra 0."
  assert(not (inOrderR r2 "Cordoba" "Rosario")) "Encuentra que la condicion es Falsa con 3 ciudades. Compara 2 contra 1."

  assert(not (inOrderR r2 "Rosario" "Baires")) "Encuentra que la condicion es Falsa con 3 ciudades. Compara 1 contra 0."

  assert( inOrderR r2  "Baires" "Cordoba") "Encuentra que la condicion es Verdadera con 3 ciudades. Compara 0 contra 2"
  assert( inOrderR r2  "Baires" "Rosario") "Encuentra que la condicion es Verdadera con 3 ciudades. Compara 0 contra 1"
  assert( inOrderR r2  "Rosario" "Cordoba") "Encuentra que la condicion es Verdadera con 3 ciudades. Compara 1 contra 2"
  assert(not (inRouteR r2 "Paradise City")) "inRouteR devuelve Falso con una ciudad que no está en la ruta de 3 ciudades."

  print("Terminados Test de Route")
  
  
  -- Test de Stack
  print("Comienzan test de Stack")

  let s0 = newS 3
  assert (freeCellsS s0 == 3) "newS crea pila vacía correctamente"
  assert (netS s0 == 0) "netS calcula bien el peso neto de una pila vacía"
  assert(testF (stackS s0 palet5)) "stackS lanza un error cuando se quiere apilar un palet muy pesado"
  assert (netS s0 == 0) "Si stackS tira error, efectivamente no se apila el palet"
  
  let s1 = stackS s0 palet1
  assert (freeCellsS s1 == 2) "freeCellsS luego de un apilado"

  let s2 = stackS s1 palet2
  assert (netS s2 == 5) "netS calcula bien el peso neto"

  let s3 = stackS s2 palet3
  assert (freeCellsS s3 == 0) "stackS no tiene celdas restantes"

  assert (testF (stackS s3 palet4)) "stackS lanza un error cuando se quieren apilar mas palets de lo que la altura permite."

  assert((holdsS s1 palet2 rutaStack) == False) "holdS no deja apilar un palet con destino antes"

  let s4 = stackS s0 palet1
  let s5 = stackS s4 palet1
  let s6 = popS s5 "BuenosAires"
  assert (freeCellsS s6 == 3) "popS elimina correctamente palets con destino"

  -- El stack no se encarga de revisar si el palet de input a holdS esta en la ruta, esto se debe a que este chequeo es a nivel camion.
  assert( (holdsS s0 palet7 rutaStack)) "holdsS acepta palet que no esta en la ruta si la lista esta vacia, no es trabajo de Stack revisar esto"
  print("Terminaron test de Stack")
  
  
  -- Tests de Truck 
  print("Comienzan Test de Truck")
  -- Crear palets
  let palet1 = newP "BuenosAires" 3
  let palet2 = newP "Rosario" 2
  let palet3 = newP "Cordoba" 5
  let palet4 = newP "Cordoba" 2
  let palet5 = newP "Chubut" 2
  let palet6  = newP "Mendoza" 4
  let palet7 = newP "Salta" 4

  let truck = newT 2 3 rutaCorta  -- Camión con 2 bahías, cada una con altura 3

  -- Test: Crear camión
  assert (freeCellsT truck == 6) "newT crea un camión con 2 bahías de altura 3 vacías"

  -- Test: Cargar un palet
  let truck1 = loadT truck palet1
  assert (freeCellsT truck1 == 5) "loadT reduce las celdas disponibles tras cargar un palet"

  -- Test: Cargar más palets
  let truck2 = loadT truck1 palet2
  assert (freeCellsT truck2 == 4) "loadT sigue funcionando con múltiples palets"

  -- Test: Peso neto del camión
  assert (netT truck2 == 5) "netT calcula correctamente el peso total"

  -- Test: Descarga en una ciudad
  let truck3 = unloadT truck2 "BuenosAires"
  assert (freeCellsT truck3 == 5) "unloadT descarga correctamente en la ciudad"


  -- Test: Crear otro camión
  let truck2 = newT 3 2 rutaLarga  -- Camión con 3 bahías, cada una con altura 2

  assert (freeCellsT truck2 == 6) "newT crea un camión con 3 bahías, cada una con altura 2 vacías"

  -- Test: Llenamos el camion de palets
  let truck_a = loadT truck2 palet1 -- Baires

  assert (freeCellsT truck_a == 5) "Load T carga bien elemento 1 con 3 bahias de altura 2"
  let truck_b = loadT truck_a palet1
  assert (freeCellsT truck_b == 4) "Load T carga bien elemento 2 con 3 bahias de altura 2"

  let truck_c = loadT truck_b palet4--Cordoba
  assert (freeCellsT truck_c == 3) "Load T carga bien elemento 3 con 3 bahias de altura 2"

  let truck_d = loadT truck_c palet4
  assert (freeCellsT truck_d == 2) "Load T carga bien elemento 4 con 3 bahias de altura 2"

  let truck_e = loadT truck_d palet5--Chubut
  assert (freeCellsT truck_e == 1) "Load T carga bien  elemento 5 con 3 bahias de altura 2"

  let truck_f = loadT truck_e palet5

  assert (freeCellsT truck_f == 0) "loadT reduce las celdas disponibles a 0 tras llenar de palets."
  
  assert ((freeCellsT (loadT truck_f palet5) == freeCellsT truck_f) && (netT (loadT truck_f palet5) == netT truck_f)) "loadT deja el mismo camion si está lleno" 

  -- Test 8: Descargar palets
  let truck_g = unloadT truck_f "BuenosAires"
  assert (netT truck_g == 8) "Unload descargó bien el peso"
  assert (freeCellsT truck_g == 2) "Unload descargó bien el palet."


  -- Test: Cargar palets que no pueden ser por el orden

  let truck_h = loadT truck_g palet6 -- al cargar palet 6, solo me queda un espacio en esa bahia, entonces no podria cargar algo que vaya mas lejos que mendoza

  assert (freeCellsT (loadT truck_h palet7) == (freeCellsT  truck_h)) "No carga Palet que no respeta orden"


  -- Test: Crear camion con altura 0 falla (efectivamente se lanza un error, que por algun motivo testF no captura, por lo que lo dejamos comentado)
  --  print((newT 2 0 ruta)) 

  -- Test: camion de una sola bahia
  let truck = newT 1 4 rutaCorta  -- Camión con 1 bahías, cada una con altura 4
  assert (freeCellsT truck == 4) "Se crea correctamente camion de 1 bahia. Las celdas vacias son las correctas"
  assert (netT truck == 0) "Se crea correctamente camion de 1 bahia. El peso es 0."

  -- Test: Cargar palets en orden correcto
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
  print("Finalizan Tests de Truck")

  putStrLn "\n✅ Todos los tests finalizados."
