valorAbsoluto x | x<0 = -x
                | otherwise = x

esBiciesto x | mod x 4 == 0 && mod x 100 /= 0 = True
             | otherwise = False 

factorial x  | x==1 =1
             | x <= 0 = error "factorial admite solo positivos"
             | otherwise = factorial (x-1) * x

divisores x =  [n| n <- [1..x+1] , mod x n ==0]

esPrimo x = length (divisores x) == 2

cantDivisoresPrimos x = length[n| n <- divisores x , esPrimo n]


-- Ejercicio 3

inverso x | x==0 = Nothing
          | otherwise = Just (1/x)

-- b. Definir la funci´on aEntero :: Either Int Bool →Int que convierte a entero una expresi´on que puede ser
-- booleana o entera. En el caso de los booleanos, el entero que corresponde es 0 para False y 1 para True.


aEntero :: Either Int Bool -> Int
aEntero (Left x) =  floor x
aEntero (Right x) | x == False = 0
                 | x == True = 1


-- Ej 4 Sale con closure

-- b. difPromedio :: [Float] →[Float] que dada una lista de n´umeros devuelve la diferencia de cada uno con el
--promedio general. Por ejemplo, difPromedio [2, 3, 4] eval´ua a [-1, 0, 1].


difPromedio lista = [prom- n |n <- lista  , True] where
         prom = fromIntegral(sum lista) / fromIntegral(length lista)


-- Ejercicio 5
data AB a = Nil | Bin (AB a) a (AB a)

-- a. nullTree :: AB a →Bool que indica si un ´arbol es vac´ıo (i.e. no tiene nodos).


nullTree Nil = True

nullTree _ = False

-- b. negTree :: AB Bool →AB Bool que dado un ´arbol de booleanos construye otro formado por la negaci´on de
cada uno de los nodos.

negTree Nil = Nil

negTree Bin (Arb booleano Arb2) = Bin (negTree (Arb) (not booleano) negTree(Arb2))  

-- c. prodTree :: AB Int →Int que calcula el producto de todos los nodos del ´arbol.

prodTree Nil = 1

prodTree Bin(rama1 a rama2) = a * (prodTree rama1)* prodTree(rama2)
        

--Ejercicio 9 F

--La siguiente expresi´on intenta ser una definici´on de una lista (infinita) de triplas pitag´oricas:
--pitag´oricas :: [(Integer,Integer,Integer)]
--pitagoricas = [(a,b,c) | a <- [1..], b <-[1..], c <- [1..], a^2 + b^2 == c^2]
--Explicar por qu´e esta definici´on no es ´util. Dar una definici´on mejor.

--Aca primer fija a, despues b y desp c entonces genera al infinito:

pitagoricas2 = [(a,b,c) | c <- [1..], a <- [1..c], b <-[1..c] , a^2 + b^2 == c^2] --Ahora este fija primero c y a y b como muchisimo son c asi q ahora estamos bien

--Ejercicio 11
--Usando listas por comprensi´on, escribir la funci´on partir::[a]->[([a],[a])] que, dada una lista xs,
--devuelve todas las maneras posibles de partirla en dos sublistas xs1 y xs2 tales que xs1++xs2 == xs.
--Ejemplo: partir [1,2,3] devuelve [([],[1,2,3]),([1],[2,3]),([1,2],[3]),([1,2,3],[])]

partir lista = [(take x lista, drop x lista) | x <- [0..length lista]]

--Ejercicio 15
--i. Definir la funci´on partes, que recibe una lista L y devuelve la lista de todas las listas formadas por los
--mismos elementos de L, en su mismo orden de aparici´on.
--Ejemplo: partes [5,1,2] → [[], [5], [1], [2], [5,1], [5,2], [1,2], [5,1,2]]
--(no necesariamente en ese orden).

partes lista = foldr unaFuncion [] lista

unaFuncion lista_de_listas valor = lista_de_listas++ [l++[valor] | l <- lista_de_listas]