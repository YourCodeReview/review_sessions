class Class1 extends Aclass {

  constructor(n) {
    super(n);
  }

  swap(arr, a, b) {
    let temp = arr[a];
    arr[a] = arr[b];
    arr[b] = temp;
  }

// Данный метод сортировки - "сортировка пузырьком", Compare лежит в app.js
  sort(compare = this.defaultCompare) {
    let length = this.n;
    let arr = this.numbers;
    for (let i = 0; i < length; i++) {
      for (let j = 0; j < length - 1 - i; j++) { // refer to note below
        if (compare(arr[j], arr[j + 1]) === Compare.BIGGER_THAN) {
          this.swap(arr, j, j + 1);
        }
      }
    }
    for (let i = 0; i < length; i++) arr[i] = this.f(arr[i]);
    return arr;
  }
}
