import Route
import TestExceptions (testF)

-- Crear función de testeo
assert :: Bool -> String -> IO ()
assert cond msg = putStrLn $ if cond then "✅ OK: " ++ msg else "❌ Falló: " ++ msg

main :: IO ()
main = do
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

