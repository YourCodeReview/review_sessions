class Class2 extends Aclass {
  constructor(n) {
    super(n);
  }

  merge(left, right, compare) {
    let i = 0;
    let j = 0;
    const result = [];
    while (i < left.length && j < right.length) {
      result.push(compare(left[i], right[j]) === Compare.LESS_THAN ? left[i++] : right[j++]);
    }
    return result.concat(i < left.length ? left.slice(i) : right.slice(j));
  }
// метод сортировки "сортировка слиянием". Compare лежит в app.js
  sort(arr = this.factorial(), compare = this.defaultCompare) {

    if (arr.length > 1) {
      let temp = new Class2;
      temp.numbers = arr;
      let length = temp.numbers.length;
      const middle = Math.floor(length / 2);
      const left = temp.sort(temp.numbers.slice(0, middle), compare);
      const right = temp.sort(temp.numbers.slice(middle, length), compare);
      arr = this.merge(left, right, compare);
    }

    return arr;
  }

}
