# Assignment 1 \- Vision and Scope

## Team 10 

September 1, 2025

1. ### Project Vision

   1. Vision Statement

   For the Avid swimmer who dislikes going to crowded pools and only likes saunas, We have craving for a splash.

   Build a reliable, secure backend service that centralizes Icelandic swimming pool data, supports user accounts and check-ins. We also provide role-based admin tools for pool managers so they can edit their pool description. 

   Unlike other competitors that only have a website that you check if have been to a pool or the Reykjavík websites that doesn’t have more information about the pool in general or allow you to give the pools and such a review all in one place.

   2. Business opportunity

   The opportunity is that the [https://reykjavik.is/sundlaugar](https://reykjavik.is/sundlaugar) only serves the swimming pools that are under the Reykjavík municipality and not other  municipalities that exist in Iceland there information is just a statement of facts that they have a swimming pool,indoor pool or a sauna and nothing about the size,quality or temperature of the hot tubs. You could see the traffic there and a number to be specific of the number of people there or you could give a review on google but what we want to create is a one stop shop for Icelandic people to get information,give reviews and see the traffic in an easy to use app since the only other competitor is a website that you check that you have gone to a pool and nothing more just a check mark while Google is a multi billion dollar company that thrives on smaller companies advertising on Google adsense,the search engine and Youtube ads so even though they could quite literally do an app like this within a couple of days or weeks and our idea would be blown out of the water but they aren’t gonna do that they earn their money from the little guys and anything big enough that threaten them or give them even close to 0.1% increase in their total revenue then we can expect to be bought out by Meta,Amazon,Alphabet or any of the big tech companies that buy out start ups and we would be very happy and rich men and then it doesn’t matter that we are their competitor and doing something that could hurt them and Reykjavík is a municipality and does not think in revenue. And the other website has a simple functionality and that is a check-in.

1.3 Limitations and exclusions  
		We are limiting the idea for Iceland as whole at most and to the pools that  
fall under Reykjavík municipality as a realistic option for the types of pools  
And other such stuff that could be available at the pool for our database.   
Since it is only possible to get answers to some stuff by either contacting   
Each and every town pool in Iceland or go there ourselves and that does   
Not fall under the scope of a realistic class idea that we are not planning to launch officially anywhere and thus has no need for completely real data when we can just gather data from 3-4 pools in Reykjavík and work from there. And we’ll try and keep the options of possible amenities to a realist but still flexible degree for example Breiðholts pool is also part of Worldclass and thus if you are a member of Worldclass can enter the pool for no extra cost and thus you can think of the gym as a amenity   
 

2. ### Use cases

   1. Use Case 1: Create User Account

   1\.**Create User Account** \- 

   Register a new user account for accessing pool review services

   4\. **Primary actor**

   User (potential pool reviewer)

   6\.**Preconditions**

* User has the mobile application installed and open  
* User has access to a valid email address  
* System database is operational and accessible  
  7\.**Success guarantee**  
    
* User account is successfully created and stored in the database  
* Account contains username, email, and securely encrypted password  
* User receives email verification  
* User can successfully log in to the newly created account after verification  
    
  8\.**Main success scenario**  
1) User opens the application and selects "Create Account"  
2) System displays account creation form  
3) User enters desired username  
4) System validates username is unique and available  
5) User enters valid email address  
6) System validates email format and uniqueness  
7) User creates password meeting security requirements (minimum length, at least 1 number, 1 uppercase letter, 1 special character)  
8) User re-enters password for confirmation  
9) System validates password match and security requirements  
10) System creates account with encrypted password storage  
11) System sends verification email to user's email address  
12) User clicks verification link in email  
13) System activates account  
14) User successfully logs in with new credentials  
    9\.**Extensions/Alternate scenarios**  
    4a. Username already taken:

    4a1. System displays error message "Username already exists"  
    4a2. User enters different username  
    4a3. Return to step 4  
    6a. Email already registered:  
    	6a1. System displays error message "Email already registered"  
    6a2. User enters different email address  
    6a3. Return to step 6  
    7a. Password doesn't meet security requirements:

    7a1. System displays specific password requirement violations

    7a2. User modifies password to meet requirements  
    7a3. Return to step 7

    13\.**Miscellaneous/Open issues**  
* How detailed the user account should be  
* What data is the minimum needed for the user account  
* Should users be able to delete their own accounts

	  
		  
	

## 

2. Use case 2:Add Pool Amenities and Features

   1\.**Add Pool Amenities and Features** \- 

   Add various amenities, pool types, and features to a pool facility

   4\. **Primary actor**

   Pool Administrator/Owner (verified pool operator)

   6\.**Preconditions**

* Pool administrator has verified admin account and is logged into the system  
* Pool facility record exists in the system  
* Administrator has necessary permissions to modify pool information  
* System database is operational and accessible  
  7\.**Success guarantee**  
* New amenities, pool types, or features are successfully added to the pool facility  
* Each added item is properly categorized and stored in the database  
* Added items are available for individual reviews by users  
* Pool facility information is updated and visible to users  
* System maintains data integrity and proper relationships between pool and its amenities  
  8\.**Main success scenario**  
1. Pool administrator logs into the admin dashboard  
2. System displays pool management interface  
3. Administrator selects their pool facility from the list  
4. System displays current pool amenities and features  
5. Administrator clicks "Add New Amenity/Feature"  
6. System displays amenity/feature creation form with category options  
7. Administrator selects amenity category (pool types, saunas, amenities, operational info)  
8. System displays category-specific input fields  
9. Administrator enters amenity details (name, description, specifications, operating hours)  
10. Administrator uploads relevant images (optional)  
11. System validates input data and image formats  
12. Administrator reviews entered information  
13. Administrator confirms and submits the new amenity/feature  
14. System saves the amenity/feature to the database  
15. System updates pool facility record with new amenity association  
16. System displays success confirmation with the newly added item  
17. Administrator can continue adding more items or exit  
    9\.**Extensions/Alternate scenarios**  
    6a. No category fits the new amenity:

    6a1. Administrator selects "Other" or "Custom Category"  
    6a2. System prompts for custom category creation  
    6a3. Administrator provides category name and description  
    6a4. Return to step 8  
    9a. Required fields are missing:  
    	9a1. System highlights missing required fields  
    9a2. Administrator completes missing information  
    9a3. Return to step 9  
    11a. Data validation fails:

    11a1. System displays specific validation errors (invalid temperature range, conflicting hours, etc.)

    11a2. Administrator corrects the invalid data  
    11a3. Return to step 11  
    

    13\.**Miscellaneous/Open issues**  
    	What happens to user reviews if an amenity is removed or significantly modified?  
    	Should there be version control for amenity information changes?  
    What approval process is needed for new custom categories?  
    3. Use case 3:Create Pool/Amenity Review

    1\.**Create Pool/Amenity Review** \- 

    Submit a review rating and optional text for a pool facility or specific amenities

    4\. **Primary actor**

    Registered User (authenticated pool visitor)

    6\.**Preconditions**

* User has a verified account and is logged into the system  
* User has selected a specific pool facility to review  
* Pool facility and its amenities exist in the system  
* System database is operational and accessible  
  7\.**Success guarantee**  
* Review is successfully created and saved in the database  
* Review rating (1-5 penguins) is recorded for selected items  
* Optional text review is stored if provided  
* Review is associated with the correct user, pool, and/or specific amenities  
* Review becomes viewable only on the reviewed item(s) pages  
* User cannot submit duplicate reviews for the same item  
  8\.**Main success scenario**  
1. User navigates to a specific pool facility page  
2. System displays pool information and available amenities  
3. User taps "Write a Review" button for the pool  
4. System displays review creation interface  
5. User selects review scope: "Entire Pool" or "Specific Amenities"  
6. System displays appropriate selection options based on choice  
7. User selects specific amenities to review (if "Specific Amenities" was chosen)  
8. System displays review form for selected items  
9. User assigns penguin rating (1-5 penguins) for each selected item  
10. User optionally writes text review for each selected item  
11. System validates rating inputs and text length limits  
12. User reviews their submission before confirming  
13. User submits the review  
14. System saves review data to database  
15. System associates review with user profile and selected items  
16. System displays success confirmation message  
17. User can view their submitted review on the respective item pages  
    9\.**Extensions/Alternate scenarios**

    5a. User changes mind about review scope:

    5a1. User selects different review scope option

    5a2. System updates interface accordingly  
    5a3. Return to step 6  
    7a. No amenities selected for specific review:

    7a1. System displays warning "Please select at least one amenity to review"

    7a2. User selects one or more amenities  
    7a3. Return to step 8  
    9a. User doesn't provide rating for selected item:  
    9a1. System highlights missing rating with error message  
    9a2. User provides required penguin rating (1-5)  
    9a3. Return to step 10  
    

    13\.**Miscellaneous/Open issues**  
    Should users be able to edit their reviews after submission?  
    What happens to reviews if an amenity is removed or significantly changed?  
    How should review moderation work for text portions?  
    What is the maximum character limit for text reviews?  
    Should there be a report/flag system for inappropriate reviews?  
    4. Brief use cases

       1. **Use case 4: Browse pools and view details** Any user can find pools and view their details without being authenticated, including viewing pool information, amenities, and reviews to help decide which facility to visit. Example: GET /pools?filters, GET /pools/{id}

       2. **Use case 5: Manage account and fully check in** Authenticated user can maintain their profiles, use check-ins, make reviews and track their visits to build a comprehensive pool-going experience and history. Example: POST /users/{id}/checkins, PATCH /users/{id}/profile

       3. **Use case 6: Admin access** Admins for each pool or general area can update their pool info, requiring a valid admin account authenticated with JWT to manage facility details and operational information. Example: PUT /pools/{id}, PATCH /pools/{id}/hours

       4. **Use case 7: Filter by closest** Users can filter pools by proximity to their current location or specified address to find the most convenient facilities nearby. Example: GET /pools?latitude={lat}\&longitude={lng}\&radius={km}

       5. **Use case 8: Search by name** Users can search for specific pools by entering facility names or partial names to quickly locate particular swimming facilities. Example: GET /pools?search={poolName}

       6. **Use case 9: Favorites** Authenticated users can add pools to their favorites list for quick access and track their preferred facilities for future visits. Example: POST /users/{id}/favorites/{poolId}, GET /users/{id}/favorites

       7. **Use case 10: Add other users as friends** Authenticated users can send friend requests to other users and build a social network to share pool experiences and recommendations. Example: POST /users/{id}/friends/{friendId}, GET /users/{id}/friends

       8. **Use case 11: View pool schedule and availability** Users can check pool operating hours, special events, and real-time capacity information to plan their visits effectively. Example: GET /pools/{id}/schedule, GET /pools/{id}/availability

       9. **Use case 12: Rate and review specific amenities** Authenticated users can provide penguin ratings (1-5) and optional text reviews for individual pool features like hot tubs, saunas, or wave pools. Example: POST /amenities/{id}/reviews, GET /amenities/{id}/reviews

    

       10. **Use case 13: Receive notifications and updates** Authenticated users can subscribe to notifications about their favorite pools including schedule changes, special events, and maintenance alerts. Example: POST /users/{id}/subscriptions, GET /users/{id}/notifications

2.5. Endpoints:

	**Auth and identity**

POST /api/auth/login \- Authenticate user credentials 

POST /api/auth/logout \- Logout user session  

POST /api/auth/refresh \- Refresh JWT token 

POST /api/auth/verify-email \- Verify email verification token 

GET /api/me \- current user profile

PATCH /api/me \- update profile

DELETE /api/me \- delete profile

**GET Endpoints (Data Retrieval)**

GET /api/{resource}/{id} \- Retrieve single resource by ID (users, pools, amenities,	reviews) 

GET /api/{resource} \- Retrieve all resources in collection 

GET /api/{resource}?sortBy={field}\&order={asc/desc} \- Retrieve resources with ordering/sorting

GET /api/{resource}?page={n}\&limit={n} \- Retrieve paginated resources

GET /api/{resource}/{id}/{subresource} \- Retrieve associated resources (user	reviews, pool amenities) 

GET /api/{resource}/stats \- Retrieve aggregated data (average ratings, totals)

GET /api/{resource}/categories \- Retrieve hierarchical data (amenity categories) 

**POST Endpoints (Resource Creation)**

POST /api/{resource} \- Create new resource (users, pools, amenities, reviews) 

POST /api/{resource}/{id}/{subresource} \- Create association between resources	

POST /api/{resource}/{id}/images \- Submit resource data with media files 

POST /api/{resource}/batch \- Submit batch data for bulk creation 

POST /api/{resource}/{id}/verify \- Trigger complex operations (verification,	validation) 

**PATCH Endpoints (Partial Updates)**

PATCH /api/{resource}/{id}/{field} \- Update single attribute of resource 

PATCH /api/{resource}/{id} \- Partially update resource details

PATCH /api/{resource}/{id}/{subresource} \- Update nested resource attributes \[3\] 

**PUT Endpoints (Complete Replacement)**

PUT /api/{resource}/{id} \- Replace resource entirely 

PUT /api/{resource}/{id}/images \- Upload or replace large file resources 

**DELETE Endpoints (Resource Removal)**

DELETE /api/{resource}/{id} \- Delete resource 

DELETE /api/{resource}/{id}/{subresource} \- Delete relationship between	resources 

DELETE /api/{resource}?ids={list} \- Bulk delete resources 

DELETE /api/{resource}/{id}?soft=true \- Soft delete resource 

DELETE /api/{resource}?{criteria} \- Delete based on criteria 

3. ### Project Estimation and Prioritization

| Use case | Time estimation | Priority |
| ----- | ----- | ----- |
| Use case 1 | 2 weeks | P1 |
| Use case 2 | 2-3 weeks | P1 |
| Use case 3 | 2 weeks | P2 |
| Brief use cases | 1ish week per BUC | P3 |

4. ### Project plan & Schedule 

| Week | Use Cases | Expected Hours | P.O | Sprint | Consultation |
| :---- | :---- | :---- | :---- | :---- | :---- |
| 1(Sept.7-14) | UC1,UC2,Post | 30 | MSK | 1 | A1 Presentation |
| 2(Sept.14-21) | UC1,UC2,Post | 30 | SAB | 1 | Model drafts |
| 3(Sept.21-28) | UC2,UC3,Get | 25 | ANB Turn in for A2(SAB) | 2 | A2 Turn in & Final touches |
| 4(Sept.28-Okt.5) | UC3,Get | 25  | ANB Presentation(SAB) | 2 | A2 Presentation |
| 5(Okt.5-12) | BUC 4,5&6, Patch | 20 | ANB | 2 | Dev support |
| 6(Okt.12-19) | BUC 7,8&9, Patch | 20 | AES | 3 | A3 Turn in & Final touches |
| 7(Okt.19-26) | BUC 10,11,12&13 | 30 | AES  | 3 | A3 Presentation |
| 8(Okt.26-Nov.2) | Put & delete | 30 | AES | 3 | Dev support |
| 9(Nov.2-9) | Put & delete | 35 | MSK Turn in(AES) | 4 | A4 Turn in & Final touches |
| 10(Nov.9-16) | Left over work | 25 | MSK Presentation(AES) | 4 | A4 Presentation |
| 11(Nov.16-23) | Left over work until presentation then not allowed to change afterwards | 25 | MSK Presentation(MSK) Turn in (MSK) | 5 | A5 Turnin \&Presentation |

5. ### Project Skeleton

[https://github.com/Lavardur/Sundbok](https://github.com/Lavardur/Sundbok)

Arnar Eyðunsson Simonsen \- addisim

Anton Benediktsson \- Lavardur

Mikael Sigurður Kristinsson \- Admentus-Dragon

Sævar Axel Bjarnason \- SaevarIII

