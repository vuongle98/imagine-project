// util function
function isInt(n: any) {
  return Number(n) === n && n % 1 === 0;
}

function isFloat(n: any) {
  return Number(n) === n && n % 1 !== 0;
}


export function sameStringArray(arr1: string[], arr2: string[]) {
  if (arr1.length !== arr2.length) return false;
  return arr1.sort().join(',') === arr2.sort().join(',')
}
