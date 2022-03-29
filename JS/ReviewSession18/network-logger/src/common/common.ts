export function omit<T>(key: keyof T, obj: T) {
  const { [key]: omitted, ...rest } = obj;
  return rest;
}
