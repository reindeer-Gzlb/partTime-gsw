// 保留小数
export function fixedValue(value, number) {
  let str = value ? value.toFixed(number) : 0;
  return str;
}
