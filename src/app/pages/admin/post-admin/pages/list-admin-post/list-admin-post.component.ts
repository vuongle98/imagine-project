import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { BaseCrudComponent } from '@shared/components/base-crud/base-crud.component';
import { PostAdminDataSource } from '../../services/post-admin.datasource';
import { DialogService } from '@shared/modules/dialog/dialog.service';
import { NotificationService } from '@shared/services/common/notificaton.service';
import { Post } from '@shared/models/blog';
import { AdminSearchPostFormComponent } from '../../components/admin-search-post-form/admin-search-post-form.component';
import { concatMap, filter, finalize, switchMap, tap } from 'rxjs';
import { AdminCreatePostFormComponent } from '../../components/admin-create-post-form/admin-create-post-form.component';

@Component({
  selector: 'app-list-admin-post',
  templateUrl: './list-admin-post.component.html',
  styleUrls: ['./list-admin-post.component.scss'],
})
export class ListAdminPostComponent
  extends BaseCrudComponent<PostAdminDataSource>
  implements OnInit, AfterViewInit
{
  listPost: Post[] = [];

  totalRows = 0;
  currentPage = 0;

  @ViewChild(AdminSearchPostFormComponent)
  adminSearchPostForm!: AdminSearchPostFormComponent;

  constructor(
    protected override dialogService: DialogService,
    protected postAdminDataSource: PostAdminDataSource,
    protected override notificationService: NotificationService
  ) {
    super(dialogService, postAdminDataSource, notificationService);
  }

  ngOnInit(): void {
    this.postAdminDataSource.loadData({
      page: this.currentPage,
      size: 10,
    });
    this.postAdminDataSource.dataSubject
      .pipe(filter((data) => !!data.content))
      .subscribe((res) => {
        this.listPost = res.content;
        this.totalRows = res.totalElements;
      });
  }

  ngAfterViewInit(): void {
    this.adminSearchPostForm.emitSearch
      .pipe(
        tap((value) => {
          this.currentPage = 0;
          this.postAdminDataSource.loadData({
            ...value,
            page: this.currentPage,
          });
        })
      )
      .subscribe();

    this.adminSearchPostForm.emitCreate
      .pipe(
        switchMap(() =>
          this.openCreate(AdminCreatePostFormComponent, {
            header: 'Create post',
            data: {},
          })
        ),
        switchMap((formValue) => this.handleCreateOrUpdate(formValue))
      )
      .subscribe();
  }

  updatePost(post: Post): void {
    this.openUpdate(AdminCreatePostFormComponent, {
      header: 'Update post',
      data: post,
    })
      .pipe(concatMap((formValue) => this.handleCreateOrUpdate(formValue)))
      .subscribe();
  }

  recoverPost(post: Post): void {
    this.openConfirm(
      'Confirm recover post?',
      'Are you sure you want to recover this post?',
      post
    )
      .pipe(
        concatMap(() => this.postAdminDataSource.recoverPost(post.id)),
        finalize(() => this.postAdminDataSource.loadData({ page: 0, size: 10 }))
      )
      .subscribe();
  }

  setFeaturePost(post: Post): void {
    this.openConfirm(
      'Confirm set feature post?',
      'Are you sure you want to mark this post is feature?',
      post
    )
      .pipe(
        concatMap(() => this.postAdminDataSource.setFeaturePost(post.id)),
        finalize(() => this.postAdminDataSource.loadData({ page: 0, size: 10 }))
      )
      .subscribe();
  }

  unsetFeaturePost(post: Post): void {
    this.openConfirm(
      'Confirm unset feature post?',
      'Are you sure you want to unset this post is feature?',
      post
    )
      .pipe(
        concatMap(() => this.postAdminDataSource.unsetFeaturePost(post.id)),
        finalize(() => this.postAdminDataSource.loadData({ page: 0, size: 10 }))
      )
      .subscribe();
  }

  deletePost(post: Post): void {
    this.openDelete(
      'Confirm delete post?',
      'Are you sure you want to delete this post?',
      post,
      true
    ).subscribe();
  }
}
