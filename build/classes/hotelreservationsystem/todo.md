# 🎯 Task

Generate a complete design and development plan in Markdown for a **Hotel Reservation System** written in Java using **Swing** (in **NetBeans IDE**). The system should support both **hotel customers and admin users**, and must:

- Support **CRUD operations**.
- Store and read data from **binary files**.
- Be implemented by a group of **3 students**.
- Use **Java Swing GUI components**.

# 📦 Features to Include

- **Room Booking**
- **Customer Registration & Login**
- **Admin Login & Dashboard**
- **Payment Processing** (with inheritance-based `Payment` subclasses)
- **Booking Cancellation**
- **Report Generation** (for admin)

# ✅ Technical Requirements

Follow the checklist from the course DPJ5531 Programming in Java:

- [x] At least **8 GUI components** (`JFrame`, `JPanel`, `JLabel`, `JTextField`, `JButton`, etc.)
- [x] At least **2 advanced components** (`JTabbedPane`, `JDateChooser` or equivalent)
- [x] At least **2 layout managers** (`BorderLayout`, `GridLayout`)
- [x] At least **2 event listener classes** (`ActionListener`, `MouseAdapter`, etc.)
- [x] At least **3 icons** (e.g. booking, cancel, report)
- [x] At least **3 exception handling scenarios** (`IOException`, `NumberFormatException`, `NullPointerException`)
- [x] Use **inheritance or aggregation** (e.g. `Payment` superclass and `CashPayment`, `CardPayment` subclasses)
- [x] Apply **file I/O using binary files** (`ObjectOutputStream`, `ObjectInputStream`)
- [x] Include **UML class diagram**
- [x] Use a helper class for **Styling (Color/Font)**

# 🏗️ Class Structure (UML Concept)

- `HotelReservationSystem` - entry point, manages data lists
- `Room` - room details (number, type, price, status)
- `User (abstract)` - superclass
  - `Customer`
  - `Admin`
- `Booking` - contains references to `Room`, `Customer`, `Payment`
- `Payment (abstract)`
  - `CashPayment`
  - `CardPayment`
- `ReportGenerator` - generates summary reports
- `FileManager` - handles binary file read/write
- `StyleConfig` - UI helper for fonts/colors

# 🖥️ GUI Design Plan

### Panels and Forms:
- `LoginForm` – admin & customer login
- `RegisterForm` – customer registration
- `BookingForm` – book a room (with date picker)
- `AdminDashboard` – tabs for rooms, bookings, reports
- `PaymentForm` – enter and confirm payment
- `CancelBookingForm` – select and cancel a booking

### Layout & Components:
- `BorderLayout` – main window
- `GridLayout` – input forms
- `JTabbedPane` – switch between admin panels
- `JTable` – show lists (bookings, rooms)
- `JComboBox` – select room type/payment method
- `JDateChooser` – pick check-in/check-out dates

# 🔐 Exception Handling Scenarios

- File not found or unable to save/load (`IOException`)
- Invalid input for number fields (`NumberFormatException`)
- Missing object references or user input (`NullPointerException`)

# 📁 Data Storage

- `customers.dat`
- `rooms.dat`
- `bookings.dat`
- `payments.dat`

Use binary serialization via `ObjectOutputStream` / `ObjectInputStream`.

# 🧾 Output Requirements

- PDF report with:
  - Cover page, Acknowledgment
  - UML diagrams
  - UI screenshots (3+ scenarios including errors)
  - Source code with comments
  - APA References
  - Appendices (task distribution, checklists)
- Java source code
- Icons & data files
- Self-peer evaluation forms

# 🧠 Tips

- Keep interface **user-friendly and consistent**
- Avoid any **plagiarized** or **AI-generated code without customization**
- Comment your code and organize packages

# 📌 Note

This prompt is for creating a **NetBeans-compatible** Java Swing project for a **Hotel Reservation System** under **DPJ5531** subject. All project and code should be original and follow academic integrity rules.
