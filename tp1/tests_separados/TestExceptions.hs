module TestExceptions (testF) where

import Control.Exception (tryJust, evaluate, SomeException)
import System.IO.Unsafe (unsafePerformIO)

-- This function checks whether evaluating a value throws an exception
testF :: Show a => a -> Bool
testF action = unsafePerformIO $ do
    result <- tryJust isException (evaluate action)
    return $ case result of
        Left _ -> True
        Right _ -> False
  where
    isException :: SomeException -> Maybe ()
    isException _ = Just ()
