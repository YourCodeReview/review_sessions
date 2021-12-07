class Aclass {
  constructor(n) {
    this.n = n;
    this.numbers = new Array(n);
    this.fill(n);
  }


  defaultCompare(a, b) {
    if (a === b) {
      return 0;
    }
    return a < b ? Compare.LESS_THAN : Compare.BIGGER_THAN;
  }
  
// функция, возвращает факториал числа, которое принимает
  f(a) {
    return (a != 1) ? a * this.f(a - 1) : 1;
  }

  fill(n) {
    for (let i = 0; i < n; i++) this.numbers[i] = Math.floor(1 + Math.random() * 10);
    
    // "fill ожно вызвать только из методов класса Aclass", как реализовать это требование и не нарушить выполнение остальных методов?
  }
// функция возвращает массив факториалов из массива чисел
  factorial() {
    let arr = new Array(this.n);
    for (let i = 0; i < this.n; i++) arr[i] = this.f(this.numbers[i]);
    return arr;
    
  }

  sort() {
    // Как написать аналог "абстрактной функции" в JS?
  }
}
