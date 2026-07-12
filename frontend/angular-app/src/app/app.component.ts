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

  constructor(public router: Router) {}

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
      this.mobileOpen = !this.mobileOpen;
    }
  }

  closeMobileSidebar() {
    if (this.isMobile && this.mobileOpen) {
      this.mobileOpen = false;
    }
  }
}
