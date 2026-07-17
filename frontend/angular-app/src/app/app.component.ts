import { Component, HostListener, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'angular-app';
  isCollapsed = false;
  isMobile = false;
  mobileOpen = false;
  // On desktop, sidebar starts visible; hamburger hides/shows it
  desktopSidebarHidden = true;

  constructor(public router: Router) { }

  ngOnInit() {
    this.checkScreenSize();
  }

  @HostListener('window:resize')
  onResize() {
    this.checkScreenSize();
  }

  checkScreenSize() {
    this.isMobile = window.innerWidth < 992;
    if (!this.isMobile) {
      this.mobileOpen = false;
    }
  }

  toggleSidebar() {
    if (this.isMobile) {
      // Mobile: slide-in overlay
      this.mobileOpen = !this.mobileOpen;
    } else {
      // Desktop: hide/show sidebar completely
      this.desktopSidebarHidden = !this.desktopSidebarHidden;
    }
  }

  closeMobileSidebar() {
    if (this.isMobile && this.mobileOpen) {
      this.mobileOpen = false;
    }
  }
}
