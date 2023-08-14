export class Queue<T> {
  items: T[] = [];
  constructor() {
    this.items = [];
  }

  enqueue(element: T) {
    this.items.push(element);
  }

  dequeue() {
    if (this.isEmpty()) return 'Queue is empty';
    return this.items.shift();
  }

  front() {
    if (this.isEmpty()) return 'No element in Queue';
    return this.items[0];
  }

  isEmpty() {
    return this.items.length == 0;
  }

  get size() {
    return this.items.length;
  }
}
