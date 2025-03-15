-- TestUnitariosStack.hs
import Stack
import Palet
import Route

-- Palets de ejemplo
palet1 = newP "BuenosAires"  3 
palet2 = newP  "Rosario"  2 
palet3 = newP  "Cordoba" 5 

ruta = newR ["BuenosAires", "Rosario", "Cordoba"]

assert :: Bool -> String -> IO ()
assert cond msg = putStrLn $ if cond then "✅ " ++ msg else "❌ Falló: " ++ msg

main :: IO ()
main = do
  let s0 = newS 3
  assert (freeCellsS s0 == 3) "newS no crea pila vacía correctamente"
  
  let s1 = stackS s0 palet1
  assert (freeCellsS s1 == 2) "freeCellsS luego de un apilado"

  let s2 = stackS s1 palet2
  assert (netS s2 == 5) "netS no calcula bien el peso neto"

  let s3 = stackS s2 palet3
  assert (length (show s3) > 0) "stackS no apila correctamente 3 palets"

  assert (not (holdsS s1 palet2 ruta)) "holdsS acepta palet con destino Despues"

  
  let s4 = stackS s0 palet1
  let s5 = stackS s4 palet1
  let s6 = popS s5 "BuenosAires"
  assert (freeCellsS s6 == 3) "popS elimina correctamente palets con destino"

  putStrLn "\n✅ Todos los tests finalizados."
