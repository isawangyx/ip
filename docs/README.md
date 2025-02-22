# ChatterBot User Guide
![Screenshot of ChatterBot](/docs/Ui.png)

## Introduction
Welcome to ChatterBot, your intelligent chatbot assistant for task management.


## Adding deadlines

ChatterBot helps you track tasks with deadlines easily. To add a deadline, enter `deadline` then a task description and a date.

### Example Usage:
```
deadline Complete quiz /by 2025-02-20
```

### Example Outcome:
```
Got it. I've added this task:
  [D][ ] Complete quiz (by: Feb 20 2025)
Now you have 1 task in the list.
```

## Finding Free Time Slots
ChatterBot helps you find the next available free time slots. To find a free time slot, enter `free` then a duration.

### Example Usage:
```
free 4h
```

### Example Outcome:
```
The nearest 4-hour free slot is on Feb 26, 2025 from 2:00 PM to 6:00 PM.
```

## Delete Tasks
Remove tasks from your list. To delete a task, enter `delete` then a task number.

### Example Usage:
```
delete 2
```

### Example Outcome:
```
Noted. I've removed this task:
  [D][ ] Submit report (by: Feb 25 2025)
Now you have 2 tasks in the list.
```