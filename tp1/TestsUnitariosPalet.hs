import Palet

-- Crear función de testeo
assert :: Bool -> String -> IO ()
assert cond msg = putStrLn $ if cond then "✅ OK: " ++ msg else "❌ Falló: " ++ msg

main :: IO ()
main = do
  let p = newP "Buenos Aires" 10
  assert(netP(p) == 10) "El peso es el correcto"
  assert(destinationP(p) == "Buenos Aires") "El destino es el correcto"