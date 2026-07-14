import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProfileService } from 'src/app/core/services/profile.service';

@Component({
  selector: 'app-profile-view',
  templateUrl: './profile-view.component.html',
  styleUrls: ['./profile-view.component.css']
})
export class ProfileViewComponent implements OnInit {
  profileForm!: FormGroup;
  user: any = null;
  msg: string | null = null;
  isSubmitting = false;
  isLoading = true;

  constructor(private fb: FormBuilder, private profileService: ProfileService) {}

  ngOnInit(): void {
    this.profileForm = this.fb.group({
      userId: [{value: '', disabled: true}],
      fullName: ['', Validators.required],
      email: [{value: '', disabled: true}, [Validators.required, Validators.email]],
      contactNumber: [''],
      address: ['']
    });

    const userStr = localStorage.getItem('user');
    let userId = '';
    if (userStr) {
      userId = JSON.parse(userStr).userId;
    }

    if (userId) {
      this.profileService.getProfile(userId).subscribe({
        next: (data) => {
          this.user = data;
          this.profileForm.patchValue(this.user);
          this.isLoading = false;
        },
        error: (err) => {
          console.error('Error fetching profile', err);
          this.isLoading = false;
        }
      });
    } else {
      this.isLoading = false;
    }
  }

  onSubmit(): void {
    if (this.profileForm.invalid) {
      this.profileForm.markAllAsTouched();
      return;
    }
    this.isSubmitting = true;
    
    const userStr = localStorage.getItem('user');
    let userId = '';
    if (userStr) {
      userId = JSON.parse(userStr).userId;
    }
    
    // getRawValue gets disabled fields as well (email, userId) which is required by DTO
    this.profileService.updateProfile(userId, this.profileForm.getRawValue()).subscribe({
      next: (updated) => {
        this.user = { ...this.user, ...updated };
        // also update local storage if full name changed
        if (userStr) {
          const u = JSON.parse(userStr);
          u.fullName = updated.fullName;
          localStorage.setItem('user', JSON.stringify(u));
        }
        this.msg = 'Profile updated successfully';
        this.isSubmitting = false;
        setTimeout(() => this.msg = null, 2500);
      },
      error: (err) => {
        console.error('Error updating profile', err);
        this.isSubmitting = false;
      }
    });
  }
}
