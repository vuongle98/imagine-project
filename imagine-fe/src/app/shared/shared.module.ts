import { NgModule } from '@angular/core';
import { LoadingComponent } from './components/loading/loading.component';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RowDirective } from './directives/row.directives';
import { CardComponent } from './components/card/card.component';
import { CardTitleComponent } from './components/card/card-title/card-title.component';
import { CardContentComponent } from './components/card/card-content/card-content.component';
import { DividerComponent } from './components/divider/divider.component';
import { VgButtonDirective } from './directives/vg-button.directive';
import { VgCardDirective } from './directives/vg-card.directive';
import { AnswerCorrectDirective } from './directives/quiz/answer-correct.directive';
import { MarkDirective } from './directives/mark.directive';
import { QuizLevelDirective } from './directives/quiz/quiz-level.directive';
import { SkeletonDirective } from './directives/quiz/skeleton.directive';
import { SecurePipe } from './pipes/secure.pipe';
import { ChatComponent } from './modules/chat/chat.component';
import { MessageComponent } from './modules/chat/message/message.component';
import { StickyButtonChatComponent } from './modules/chat/sticky-button-chat/sticky-button-chat.component';
import { ListChatComponent } from './modules/chat/list-chat/list-chat.component';
import { BaseChatComponent } from './modules/chat/base-chat/base-chat.component';
import { ChatHeaderComponent } from './modules/chat/chat-header/chat-header.component';
import { RouterModule } from '@angular/router';
import { TagUsername } from './pipes/tag-username.pipe';
import { AutofocusDirective } from './directives/auto-focus.directive';
import { FocusInputDirective } from './directives/focus-input.directive';
import { HeaderComponent } from './modules/layouts/header/header.component';
import { UserProfileComponent } from './modules/layouts/widgets/user-profile/user-profile.component';
import { SidebarComponent } from './modules/layouts/sidebar/sidebar.component';
import { MenuDirective } from './directives/layout/menu.directive';
import { MenuItemDirective } from './directives/layout/menu-item.directive';
import { DropdownItemDirective } from './directives/layout/dropdown-item.directive';
import { DropdownDirective } from './directives/layout/dropdown.directive';
import { FooterComponent } from './modules/layouts/footer/footer.component';
import { MenuComponent } from './modules/layouts/menu/menu.component';
import { FloattingButtonComponent } from './modules/floatting-button/floatting-button.component';
import { BaseLayoutComponent } from './modules/layouts/base-layout/base-layout.component';
import { NoBaseLayoutComponent } from './modules/layouts/no-base-layout/no-base-layout.component';
import { ChattingContainerComponent } from './modules/modal/chatting-container.component';
import { NotificationComponent } from './components/notification/notification.component';
import { HeaderMobileComponent } from './modules/layouts/header-mobile/header-mobile.component';
import { HeaderDirective } from './modules/table/directives/header.directive';
import { CellDirective } from './modules/table/directives/cell.directive';
import { ColumnDirective } from './modules/table/directives/column.directive';
import { PaginationComponent } from './modules/table/pagination/pagination.component';
import { TableComponent } from './modules/table/table.component';
import { DialogContentDirective } from './modules/dialog/dialog-content.directive';
import { DialogContainerComponent } from './modules/dialog/dialog-container.components';
import { TestDialogComponent } from './components/dialog/test-dialog/test-dialog.component';
import { DialogCloseDirective } from './modules/dialog/dialog-close.directive';
import { SelectSearchComponent } from './components/select-search/select-search.component';
import { NgSelectModule } from '@ng-select/ng-select';
import { BaseCrudComponent } from './components/base-crud/base-crud.component';
import { DialogHeaderDirective } from './modules/dialog/dialog-header.directive';
import { DialogBodyDirective } from './modules/dialog/dialog-body.directive';
import { DialogActionsDirective } from './modules/dialog/dialog-actions.directive';
import { ConfirmComponent } from './components/dialog/confirm-delete/confirm.component';
import { BasePageComponent } from './modules/layouts/base-page/base-page.component';
import { FormItemComponent } from './components/form-item/form-item.component';
import { PostDetailComponent } from './components/post-detail/post-detail.component';
import { CommentComponent } from './components/post-detail/comment/comment.component';
import { FormatFileSizePipe } from './pipes/file-size.pipe';
import { OpenImageViewerDirective } from './directives/open-image-viewer.directive';
import { ImageViewerComponent } from './components/dialog/image-viewer/image-viewer.component';
import { CardCategoryComponent } from './components/card/card-category/card-category.component';
import { CardExtraComponent } from './components/card/card-extra/card-extra.component';
import { BlogLayoutComponent } from './modules/blog/blog-layout/blog-layout.component';
import { SideDirective } from './modules/blog/directives/side.directive';
import { ListPostCompactComponent } from './modules/blog/list-compact-post/list-compact-post.component';
import { ListPostComponent } from './modules/blog/list-post/list-post.component';
import { PostCompactComponent } from './modules/blog/list-compact-post/post-compact/post-compact.component';
import { PostComponent } from './modules/blog/post/post.component';
import { CKEditorModule } from '@ckeditor/ckeditor5-angular';
import { ScrollToTOpDirective } from './directives/scroll-to-top.directive';
import { FileUploadComponent } from './components/file-upload/file-upload.component';

const COMPONENTS = [
  LoadingComponent,
  CardComponent,
  CardTitleComponent,
  CardContentComponent,
  DividerComponent,
  QuizLevelDirective,
  SkeletonDirective,
  ChatComponent,
  MessageComponent,
  StickyButtonChatComponent,
  ListChatComponent,
  BaseChatComponent,
  ChatHeaderComponent,
  HeaderComponent,
  UserProfileComponent,
  NotificationComponent,
  SidebarComponent,
  FooterComponent,
  MenuComponent,
  FloattingButtonComponent,
  BaseLayoutComponent,
  NoBaseLayoutComponent,
  ChattingContainerComponent,
  HeaderMobileComponent,
  TableComponent,
  PaginationComponent,
  DialogContainerComponent,
  TestDialogComponent,
  SelectSearchComponent,
  BaseCrudComponent,
  ConfirmComponent,
  BasePageComponent,
  FormItemComponent,
  PostDetailComponent,
  CommentComponent,
  ImageViewerComponent,
  CardCategoryComponent,
  CardExtraComponent,
  BlogLayoutComponent,
  ListPostCompactComponent,
  ListPostComponent,
  PostCompactComponent,
  PostComponent,
  FileUploadComponent
];

const DIRECTIVES = [
  RowDirective,
  VgButtonDirective,
  VgCardDirective,
  AnswerCorrectDirective,
  MarkDirective,
  AutofocusDirective,
  FocusInputDirective,
  MenuDirective,
  MenuItemDirective,
  DropdownItemDirective,
  DropdownDirective,
  ColumnDirective,
  CellDirective,
  HeaderDirective,
  DialogContentDirective,
  DialogCloseDirective,
  DialogHeaderDirective,
  DialogBodyDirective,
  DialogActionsDirective,
  OpenImageViewerDirective,
  SideDirective,
  ScrollToTOpDirective
];
const PIPES = [SecurePipe, TagUsername, FormatFileSizePipe];

const MODULES = [
  FormsModule,
  ReactiveFormsModule,
  RouterModule,
  NgSelectModule,
  CKEditorModule,
];

@NgModule({
  declarations: [COMPONENTS, DIRECTIVES, PIPES],
  imports: [CommonModule, MODULES],
  exports: [CommonModule, COMPONENTS, MODULES, DIRECTIVES, PIPES],
})
export class SharedModule {}
