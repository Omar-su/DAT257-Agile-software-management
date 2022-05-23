# TDA367-RATAR-Project

## DAT257-Agile-software-management

<p align="left">   
    &nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;
 

  <img src="https://www.linkpicture.com/q/Screenshot-2022-05-19-004242.png" alt="Home Page image" width="220" height="400"/>
  &nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;
  <img src="https://www.linkpicture.com/q/Screenshot-2022-05-19-004703.png" alt="Rating Page image" width="220" height="400"/>
  &nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;
 
  <img src="https://www.linkpicture.com/q/Screenshot-2022-05-19-011044.png" alt="Profiel Page image" width="220" height="400"/>

</p>


## Table of Contents

<details open="open">
  <summary> Content </summary>
  <ol>
    <li><a href="#overview"> ➤  OverView</a></li>
    <li><a href="#gitinfo"> ➤ Gitinfo</a></li>
    <li><a href="#figma"> ➤ Figma</a></li>
    <li><a href="#trello"> ➤ Trello</a></li>
    <li><a href="#getting-started"> ➤ How to run the Application? </a></li>
    <li><a href="#structure"> ➤ Structure</a></li>
    <li><a href="#links"> ➤ Links</a></li>
  </ol>
</details>

<!-- OVERVIEW -->
<h2 id="overview"> OverView</h2>
The goal of this project was to create an application, where a user can rate movies and series that he/she has watched. They can rate it based on various parameters and also add notes. This way the user gets the ability to keep track on what movies they have watched and also what they thought about them.<br><br>

The application was created by the group Eurythmics during the course DAT257 and has the following members:<br>
Omar Suliman : @omar-su <br>
Jenny Carlsson: @cajenny / @jennycarlsson <br>
Desirée Staaf: @Staafd <br>
Eugene Dvoryankov: @EugeneDvoryankov <br>
Ida Nordlund: @idanordlund <br>
Oscar Palmkvist: @DwarfMamba2 <br>
Fabian Flaa: @Foxboi <br>

<!-- Gitinfo -->
<h2 id="gitinfo"> Gitinfo</h2>
The github project contains of three folders and some gradle files. <br><br>

App: Contains the source folder of the application. More about the code will be described in the "stucture" section. The app folder also includes a folder for the JUnit tests.<br><br>

Documents: Contains all of the documents for thw project. This includes:<br>
 <ul>
  <li>Business model canvas</li>
  <li>Project scope- first mockup</li>
  <li>Project scope description</li>
  <li>Social contract</li>
  <li>Folder containing our team relfections</li>
  <li>Folder containing individual reflections for each team member</li>
</ul> 

<!-- Figma -->
<h2 id="figma"> Figma</h2>
To create the mockup for our the application we used the software Figma. Here we tried out different design solutions, which we showed the stakeholder to decide the direction we wanted to go. We also created certain components in Figma which we exported into our android studio project.
<br><br>
Link: https://www.figma.com/file/isUs2WyrVKVgE0rtTUPVfb/Prototyping?node-id=0%3A1 

<!-- Trello -->
<h2 id="trello"> Trello</h2>
Trello was used as a planning tool for our project. We have used two different trello boards, one for project planning and one as our scrum board. <br><br>

Here is the link to the Trello workspace: [Trello Backlog](https://trello.com/b/6arqg1PY/user-stories)

<!-- Setup -->
<h2 id="getting-started"> How to run the Application? </h2>
If you don't have an android phone you will need to download an android virtual machine to run the app on. This is easy to do in Android Studio.

<ol>
  <li>Download <a href="https://developer.android.com/studio">Android Studio</a> och follow the  instructions.</li>
  <li>Clone this repo in Android Studio using "get from version control" alternativ. Then just copy paste the link of the repo.</li>
  <li>Make sure that you already have a Virtual device in AVD manager. If not download a "Pixel 3a API 30" which should work just fine.</li>
  <li>Build the project and run the MainActivity.</li>
</ol>

<!-- Structure -->
<h2 id="structure"> Structure</h2>
The structure of the project consists of the app and the documents. In the app you find the source code in src map in which you can navigate to the main map. In the main map you find the res map which has all xml files and all rescource used for design. In the eurythmics map you can find all the code that runs the program. There are 3 maps, model/api, view/fragments, viewmodels and a main class which runs the whole program. 

&nbsp;

The model/api has all the code for the api implementationas well as the database and some model classes that represents objects and apply logic. 


In the view map you will find all view components meaning all classes that connect the model with the gui and all adapters which display the list items on recycle views. 

In the viewModel map you will find the classes that connects the backend/model to the frgments/adapters. View models act as an observer which tells the view when something got updated. 


In the documents map at the start you find the final report, Business model canvas, Social contract, project_scope_desctiption and ProjectScope-firstMockup.


You will also find two maps the indvidual reflection map and the team reflection. In the team reflections map you find all team reflections and in the idividuell reflections map you will find a map for each member with all their own individuell reflection. 

<!-- Länkar -->
<h2 id="links"> Links </h2>
<ol>

  <li><a href="https://trello.com/b/6arqg1PY/user-stories"> Backlog (Trello) </a></li>

</ol>



