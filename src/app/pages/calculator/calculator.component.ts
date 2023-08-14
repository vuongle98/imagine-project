import { Component } from '@angular/core';
import { Queue } from 'src/app/shared/utils/queue';

@Component({
  selector: 'app-calculator',
  templateUrl: './calculator.component.html',
  styleUrls: ['./calculator.component.scss'],
})
export class CalculatorComponent {
  result = '';
  displayValue = '';
  error = '';

  q = new Queue<any>();

  isValidData(oldValue: any, value: any) {
    let oldValueArr = oldValue.trim().split(' ');

    let preValue = oldValueArr[oldValueArr.length - 1];

    if ((isNaN(preValue) && isNaN(value)) || (preValue == '' && isNaN(value)))
      return false;

    return true;
  }

  clickButton(value: any) {
    if (!this.isValidData(this.displayValue, value)) return;

    if (!isNaN(value) || value == '.') {
      this.displayValue += value;
    } else if (isNaN(value)) {
      this.displayValue += ' ' + value + ' ';
    }
  }

  clickAC() {
    this.displayValue = '';
    this.result = '';
  }

  clickCal() {
    let oldValueArr = this.displayValue.trim().split(' ');

    let preValue = oldValueArr[oldValueArr.length - 1] as any;

    if (isNaN(preValue)) {
      return;
    } else {
    }

    this.result = this.calculate();
  }

  calculate() {
    let arr = this.displayValue.split(' ');

    for (const value of arr) {
      this.q.enqueue(value);
    }

    let tmp = 0;

    while (this.q.size > 1) {
      let val = this.q.dequeue();

      if (!isNaN(val)) {
        tmp = parseFloat(val || '');
      } else {
        const val2 = parseFloat(this.q.dequeue() || '');
        switch (val) {
          case '+':
            tmp = tmp + val2;
            this.q.enqueue(tmp);
            break;
          case '-':
            tmp = tmp - val2;
            this.q.enqueue(tmp);
            break;
          case '*':
            tmp = tmp * val2;
            this.q.enqueue(tmp);
            break;
          case '/':
            tmp = tmp / val2;
            this.q.enqueue(tmp);
            break;
          case '%':
            tmp = tmp % val2;
            this.q.enqueue(tmp);
            break;
        }
      }
    }
    return this.q.dequeue();
  }
}

// handle button click

// const clickCalBtn = () => {
//   const value = document.getElementById('displayText').innerText;
//   let oldValueArr = value.trim().split(' ');

//   let preValue = oldValueArr[oldValueArr.length - 1];

//   if (isNaN(preValue)) {
//     document.getElementById('errors').innerText = 'Nhập sai kìa má';
//     return;
//   } else {
//     document.getElementById('errors').innerText = '';
//   }

//   const result = calculate(value);
//   document.getElementById('result').innerText = isFloat(result)
//     ? result.toFixed(2)
//     : result;
// };
