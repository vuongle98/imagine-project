import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import DecoupledEditor from '@ckeditor/ckeditor5-build-decoupled-document';
import { Category, Post } from '@shared/models/blog';
import { DIALOG_DATA } from '@shared/modules/dialog/constants';
import { DialogRef } from '@shared/modules/dialog/dialog-ref';
import { CategoryService } from '@shared/services/rest-api/blog/category.service';
import { BehaviorSubject, debounceTime, switchMap, tap } from 'rxjs';

@Component({
  selector: 'app-admin-create-post-form',
  templateUrl: './admin-create-post-form.component.html',
  styleUrls: ['./admin-create-post-form.component.scss'],
})
export class AdminCreatePostFormComponent {
  createPostForm!: FormGroup;

  listCategory: Category[] = [];

  currentSearchCategoryKeyword = '';
  searchCategoryChange$ = new BehaviorSubject('');

  editor = DecoupledEditor;

  constructor(
    private fb: FormBuilder,
    private dialogRef: DialogRef,
    @Inject(DIALOG_DATA) private data: Post,
    private categoryService: CategoryService
  ) {
    this.createPostForm = this.fb.group({
      title: [''],
      description: [''],
      categoryId: [null],
      content: [''],
      file: [null],
    });

    if (data) {
      this.createPostForm.patchValue({
        ...data,
        categoryId: data?.category?.id,
      });
    }

    this.onSearchCategory('');
  }

  onSearchCategory(value: string) {
    this.currentSearchCategoryKeyword = value;

    this.searchCategoryChange$
      .asObservable()
      .pipe(
        debounceTime(300),
        switchMap(() =>
          this.categoryService.findCategories({ likeName: value })
        ),
        tap((res) => {
          this.listCategory = res.content;

          this.createPostForm.patchValue({categoryId: this.listCategory[0]?.id});
        })
      )
      .subscribe();
  }

  onSubmit() {
    this.dialogRef.close({ id: this.data?.id, ...this.createPostForm.value });
  }

  onScrollToEnd() {
    console.log('scroll to end');
  }

  onReady(editor: DecoupledEditor) {
    const element = editor.ui.getEditableElement();
    const parent = element?.parentElement;

    parent?.insertBefore(editor.ui.view.toolbar.element!, element!);
  }
}
