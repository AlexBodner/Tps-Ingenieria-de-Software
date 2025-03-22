-- TestUnitariosStack.hs
import Stack
import Palet
import Route
import TestExceptions (testF)
import Control.Monad.RWS (MonadState(put))

-- Palets de ejemplo
palet1 = newP "BuenosAires"  3 
palet2 = newP  "Rosario"  2 
palet3 = newP  "Cordoba" 5 
palet4 = newP  "Mendoza"  1
palet5 = newP  "Rosario"  11
palet6 = newP  "Cordoba"  8
palet7 = newP  "Chubut"  1

-- Ruta de ejemplo
ruta = newR ["BuenosAires", "Rosario", "Cordoba", "Mendoza"]

-- Función de testeo
assert :: Bool -> String -> IO ()
assert cond msg = putStrLn $ if cond then "✅ OK: " ++ msg else "❌ Falló: " ++ msg

main :: IO ()
main = do
  -- Los mensajes de los tests son del caso exitoso. Si salio bien o mal es indicado con el tick o la cruz roja.
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

  assert((holdsS s1 palet2 ruta) == False) "holdS no deja apilar un palet con destino antes"

  let s4 = stackS s0 palet1
  let s5 = stackS s4 palet1
  let s6 = popS s5 "BuenosAires"
  assert (freeCellsS s6 == 3) "popS elimina correctamente palets con destino"

  assert(not (holdsS s0 palet7 ruta)) "holdsSno acepta palet que no esta en la ruta"

  putStrLn "\n✅ Todos los tests finalizados."
