import * as React from 'react';

export function useForceUpdate(): VoidFunction {
  return React.useReducer(() => ({}), {})[1];
}
