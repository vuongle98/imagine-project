import { NgModule } from '@angular/core';
import { NzImportModule } from '../nz-import.module';
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
import { ChatComponent } from './modules/chat/componenets/chat/chat.component';
import { MessageComponent } from './modules/chat/componenets/message/message.component';
import { StickyButtonChatComponent } from './modules/chat/componenets/sticky-button-chat/sticky-button-chat.component';
import { ListChatComponent } from './modules/chat/componenets/list-chat/list-chat.component';
import { BaseChatComponent } from './modules/chat/componenets/base-chat/base-chat.component';
import { ChatHeaderComponent } from './modules/chat/componenets/chat-header/chat-header.component';
import { RouterModule } from '@angular/router';
import { TagUsername } from './pipes/tag-username.pipe';
import { AutofocusDirective } from './directives/auto-focus.directive';
import { FocusInputDirective } from './directives/focus-input.directive';
import { HeaderComponent } from './modules/layouts/header/header.component';
import { UserProfileComponent } from './modules/layouts/widgets/user-profile/user-profile.component';
import { NotificationComponent } from './modules/layouts/widgets/notification/notification.component';
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
];

const PIPES = [SecurePipe, TagUsername];

const MODULES = [FormsModule, ReactiveFormsModule, RouterModule];

@NgModule({
  declarations: [COMPONENTS, DIRECTIVES, PIPES],
  imports: [CommonModule, NzImportModule, MODULES],
  exports: [
    NzImportModule,
    CommonModule,
    COMPONENTS,
    MODULES,
    DIRECTIVES,
    PIPES,
  ],
})
export class SharedModule {}
