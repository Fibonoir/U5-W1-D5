import { Component } from '@angular/core';
import { WorkstationService } from './services/workstation.service';
import { BookingService } from './services/booking.service';
import { UserService } from './services/user.service';
import { IWorkstation } from './interfaces/workstation';
import { IUser } from './interfaces/user';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  selectedItem: String = 'Create User'
  // Search form fields
  searchType: string = 'PRIVATE';
  searchCity: string = 'Rome';
  workstations: IWorkstation[] = [];

  // User creation form
  newUser: IUser = { username: '', fullName: '', email: '' };
  userMessage: string = '';

  // Booking form
  bookingUsername: string = '';
  bookingWorkstationId: number | null = null;
  bookingDate: string = '';
  bookingMessage: string = '';

  constructor(
    private workstationService: WorkstationService,
    private userService: UserService,
    private bookingService: BookingService
  ) {}

  searchWorkstations() {
    this.workstationService.search(this.searchType, this.searchCity).subscribe({
      next: (res) => (this.workstations = res),
      error: (err) => console.error(err),
    });
  }

  createUser() {
    if (!this.newUser.username.trim() || !this.newUser.fullName.trim() || !this.newUser.email.trim()) {
      this.userMessage = "Fields shouldn't be empty"
      return
    }
    this.userService.createUser(this.newUser).subscribe({
      next: (res) => {
        this.userMessage = `User ${res.username} created successfully!`;
        this.newUser = { username: '', fullName: '', email: '' };
      },
      error: (err) => {
        console.error(err);
        this.userMessage = 'Error creating user.';
      },
    });
  }

  createBooking() {
    if (!this.bookingUsername || !this.bookingWorkstationId || !this.bookingDate) {
      this.bookingMessage = 'Please fill all booking fields.';
      return;
    }

    this.bookingService.createBooking(this.bookingUsername, this.bookingWorkstationId, this.bookingDate).subscribe({
      next: (res) => {
        this.bookingMessage = `Booking created (ID: ${res.bookingId}) for ${res.username} on ${res.date}.`;
      },
      error: (err) => {
        console.error(err);
        this.bookingMessage = 'Error creating booking.';
      }
    });
  }
}
