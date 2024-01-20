# Overview

The i-Wish project is a desktop application designed to bring joy and happiness by facilitating the process of sharing and fulfilling wishes among friends. Users can connect with friends, create and manage wish lists, and contribute towards fulfilling the wishes of their friends. The application is divided into client-side and server-side functionalities to ensure smooth communication and data management.

# Client-side Specifications

1. Register/Sign-in
   Allows users to create an account or sign in to an existing one.

2. Add/Remove Friend
   Enables users to add or remove friends from their list.

3. Accept/Decline Friend Request
   Gives users the ability to accept or decline incoming friend requests.

4. Create, Update, Delete My Wish List
   Allows users to manage their own wish list by creating, updating, or deleting items.

5. View My Friends List
   Displays a list of friends connected to the user's account.

6. View My Friends Wish List
   Shows the wish list of selected friends, allowing users to explore and contribute.

7. Contribute in Buying Items
   Permits users to contribute a specific amount of money towards buying items from their friends' wish lists.

8. [As Buyer] Receive Notification
   Receive a notification upon the completion of a gift item's price.

9. [As Receiver] Receive Notification
   Get notified when an item from the wish list has been bought by specific friend(s).

10. Friendly GUI
    A user-friendly graphical interface to ensure a delightful experience for the users.

# Server-side Specifications

11. Start/Stop
    Controls the server's start and stop functionalities.

12. Manipulate the Database
    Manages database connections.
    Executes queries to fetch and update data.
    Supports adding items to the database for users to build their wish lists.
13. Handles Client Connections
    Manages incoming connections from clients.

14. Handles Client Requests
    Processes requests from clients related to friends, wish lists, and contributions.

15. Handles Gift Item Completion
    Notifies participants about the completion of the shares for a gift item. Informs the wish list owner that an item has been gifted by specific friend(s).
