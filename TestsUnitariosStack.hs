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
assert cond msg = putStrLn $ if cond then "✅ OK: " ++ msg else "❌ Falló: " ++ msg

main :: IO ()
main = do
  -- Los mensajes de los tests son del caso exitoso. Si salio bien o mal es indicado con el tick o la cruz roja.
  let s0 = newS 3
  assert (freeCellsS s0 == 3) "newS crea pila vacía correctamente"
  
  let s1 = stackS s0 palet1
  assert (freeCellsS s1 == 2) "freeCellsS luego de un apilado"

  let s2 = stackS s1 palet2
  assert (netS s2 == 5) "netS calcula bien el peso neto"

  let s3 = stackS s2 palet3
  
  assert (freeCellsS s3 == 0) "stackS no tiene celdas restantes"


  assert (not (holdsS s1 palet2 ruta)) "holdsS NO acepta palet con destino despues"

  
  let s4 = stackS s0 palet1
  let s5 = stackS s4 palet1
  let s6 = popS s5 "BuenosAires"
  assert (freeCellsS s6 == 3) "popS elimina correctamente palets con destino"

  putStrLn "\n✅ Todos los tests finalizados."
