import { Alert, Button } from "@chakra-ui/react"

interface ErrorFallbackProps {
  error: unknown;
  resetErrorBoundary?: () => void;
}

export function ErrorFallback({ error, resetErrorBoundary }: ErrorFallbackProps) {
  const errorMessage = error instanceof Error ? error.message : "An unexpected error occurred";
  const errorStack = error instanceof Error ? error.stack : undefined;

  return (
    <Alert.Root status="error">
      <Alert.Indicator />
      <Alert.Content>
        <Alert.Title>Something went wrong</Alert.Title>
        <Alert.Description>
          {errorMessage}
          {import.meta.env.DEV && errorStack && (
            <pre style={{ marginTop: '10px', fontSize: '12px', overflow: 'auto' }}>
              {errorStack}
            </pre>
          )}
        </Alert.Description>
      </Alert.Content>
      {resetErrorBoundary && (
        <Button onClick={resetErrorBoundary} size="sm" colorScheme="red" variant="outline">
          Try again
        </Button>
      )}
    </Alert.Root>
  )
}
