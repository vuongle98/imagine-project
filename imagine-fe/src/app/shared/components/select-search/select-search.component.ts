import { Component, forwardRef, Input, OnInit } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'app-select-search',
  templateUrl: './select-search.component.html',
  styleUrls: ['./select-search.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => SelectSearchComponent),
      multi: true
    }
  ]
})
export class SelectSearchComponent implements OnInit, ControlValueAccessor {
  @Input() listOptions: any[] = []; // This will hold the list of options fetched from the API
  selectedOptions: any[] = []; // This will hold the selected options
  searchText = ''; // This will hold the value entered in the search input field

  private onChange: any = () => {};
  private onTouch: any = () => {};

  ngOnInit(): void {
    // Fetch the list of options from the API and assign it to the listOptions array
    this.fetchOptions();
  }

  fetchOptions(): void {
    // Make an API call to fetch the list of options
    // Assign the response to the listOptions array
  }

  onOptionSelect(option: any): void {
    // Add or remove the selected option based on its current state
    const index = this.selectedOptions.indexOf(option);
    if (index !== -1) {
      this.selectedOptions.splice(index, 1);
    } else {
      this.selectedOptions.push(option);
    }

    // Update the form control value
    this.onChange(this.selectedOptions);
    this.onTouch();
  }

  onSearchChange(): void {
    // Perform filtering of options based on the search text
    // Update the listOptions array with the filtered options
  }

  loadMoreOptions(): void {
    // Make an API call to fetch more options
    // Append the new options to the listOptions array
  }

  writeValue(value: any): void {
    if (value !== undefined) {
      this.selectedOptions = value;
    }
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouch = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    // Optionally implement this method if you want to handle disabling the component
  }
}
