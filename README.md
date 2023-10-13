<h1 align="center">
  <br>
 <img src="https://github.com/prokittikun/issue/assets/76927239/2f59eb0b-d725-4d2f-b325-51c885a22479" alt="Markdownify" width="200">
  <br>
  <span style="color: #C73618;">CS211-611 Project Event Hub</span>
  <br>
</h1>


<p align="center">
    <b>โปรเจกต์นี้เป็นส่วนหนึ่งของ รายวิชา 01418211 - การสร้างซอฟต์แวร์ (Software Construction)</b> <br>
    <b>ภาคการศึกษาที่ 1 ปีการศึกษา 2566</b> <br>
</p>


<p align="center">
<a href="https://git-scm.com/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/git-scm/git-scm-icon.svg" alt="git" width="40" height="40"/> </a>
&nbsp;&nbsp;&nbsp;&nbsp;
<a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/> </a>
&nbsp;&nbsp;&nbsp;&nbsp;
<a href="https://www.w3schools.com/css/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/css3/css3-original-wordmark.svg" alt="css3" width="40" height="40"/> </a>
&nbsp;&nbsp;&nbsp;&nbsp;
<a href="https://www.figma.com/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/figma/figma-icon.svg" alt="figma" width="40" height="40"/> </a> 
&nbsp;&nbsp;&nbsp;&nbsp;
</p>

<p align="center">
  <a href="#introduction">Introduction</a> •
  <a href="#download">Download</a> •
  <a href="#installation">Installation</a> •
  <a href="#usage">Usage/Examples</a> •
  <a href="#accounts">Account Tests</a> •
  <a href="#project-structure">Project Structure</a> • 
  <a href="#conclusion">Conslusion</a>
</p>

## Introduction[![](https://raw.githubusercontent.com/aregtech/areg-sdk/master/docs/img/pin.svg)](#introduction)

<div style="display: flex; gap: 5px; margin-bottom: 10px; align-items: center; justify-content: center;" align="center">
<img src="https://github.com/prokittikun/issue/assets/76927239/815ab306-ba5a-48bf-bf75-2e27fcb89420" width="350">
<img src="https://github.com/prokittikun/issue/assets/76927239/a2bae852-fde4-4099-a4e8-f3372a3b8569" width="350">
</div>

**Event Hub** (*อิเวนต์ ฮับ*) เป็นโปรแกรมที่ถูกพัฒนาขึ้นเพื่อให้ความสะดวกในการจัดการกับกิจกรรมและอิเวนต์ต่าง ๆ ในที่เดียว โดยเราได้รวบรวมฟีเจอร์ต่าง ๆ เพื่อให้ผู้ใช้สามารถสร้างและจัดการกับอิเวนต์ได้อย่างมีประสิทธิภาพและง่ายดายมากยิ่งขึ้น ภายใน "Event Hub" มีฟีเจอร์หลากหลายที่สำคัญ เช่น ระบบสมาชิก, การสร้างอิเวนต์, การเข้าร่วมอิเวนต์, การสร้างทีมภายใต้อิเวนต์, การสร้างกำหนดการต่าง ๆ และอื่น ๆ อีกมากมายภายในระบบ



## Download[![](https://raw.githubusercontent.com/aregtech/areg-sdk/master/docs/img/pin.svg)](#introduction)
#### Jar file for Windows
- Download <b>Event-Hub-for-Windows.zip</b> from [Release](https://github.com/CS211-661/cs211-661-project-phuea-khrai-party/releases)

#### Jar file for MacOs Intel
- Download <b>Event-Hub-for-MacOs.zip</b> from [Release](https://github.com/CS211-661/cs211-661-project-phuea-khrai-party/releases)

#### Jar file for MacOs M1
- Download <b>Event-Hub-for-MacOs.zip</b> from [Release](https://github.com/CS211-661/cs211-661-project-phuea-khrai-party/releases)

## Installation[![](https://raw.githubusercontent.com/aregtech/areg-sdk/master/docs/img/pin.svg)](#introduction)
### Windows Installation
#### 1. Extract Zip File
![install_1](https://github.com/prokittikun/issue/assets/76927239/bfe3a140-696d-4f91-8398-c1bf8174dd2a)

#### 2. Select Event-Hub Folder
![install_2](https://github.com/prokittikun/issue/assets/76927239/2ba91a79-9868-41cc-922b-75ec2680732c)

#### 3. Double Click Event-Hub.jar
![install_3](https://github.com/prokittikun/issue/assets/76927239/1dc63383-d3cb-4791-bd79-4f73dd35df9a)

#### Or

```bash
# ~\Event-Hub
$ java -jar Event-Hub.jar
```

#### 5. Congratulations, you have succeeded. 🎉
![install5](https://github.com/prokittikun/issue/assets/76927239/791f7ad0-b4a4-4edd-aba0-a91e5b53e29a)

### MacOs Installation

#### 1. Double click Event-Hub.zip
<img width="289" alt="install1" src="https://github.com/prokittikun/issue/assets/76927239/7dff9a23-9315-4021-91f4-320e9d70981d">

#### 2. Open Event-Hub folder
<img width="358" alt="install2" src="https://github.com/prokittikun/issue/assets/76927239/09cc102e-bb32-4c48-9042-58fc5482525b">

#### 3. Double click Event-Hub.jar
<img width="1032" alt="install3" src="https://github.com/prokittikun/issue/assets/76927239/620e533d-3024-47e9-a6d8-2aba1198ecfb">

#### Or
```bash
# ~\Event-Hub
$ java -jar Event-Hub.jar
```

#### 4. Congratulations, you have succeeded. 🎉
<img width="1312" alt="install4" src="https://github.com/prokittikun/issue/assets/76927239/e5224e11-53a4-4f0f-a3c5-c7363968d226">

## Usage[![](https://raw.githubusercontent.com/aregtech/areg-sdk/master/docs/img/pin.svg)](#introduction)

This is User Manual [Guide](https://github.com/CS211-661/cs211-661-project-phuea-khrai-party/blob/develop/data/user-guide.pdf) or click according to the picture
![example](https://github.com/prokittikun/issue/assets/76927239/7d495146-e1bb-4dfb-9322-c8bfafeccf29)


## Accounts[![](https://raw.githubusercontent.com/aregtech/areg-sdk/master/docs/img/pin.svg)](#introduction)

| username                  | password | role  |
|--------------------------|-------|-------|
| admin | 1234 | admin |
| Yada       | 2609 | user  |
| Jimin  | 1111      | user  |
| star     | 2468 | user |
| kura  |9999| user |

## Project Structure[![](https://raw.githubusercontent.com/aregtech/areg-sdk/master/docs/img/pin.svg)](#introduction)
<pre>
📦cs211-661-project-phuea-khrai-party
┣ 📂data(เป็นเหมือน Database ที่ใช้เก็บข้อมูล)
┃ ┣ 📂event(เก็บข้อมูลประเภท Event)
┃ ┃ ┣ 📜activity.csv(เก็บข้อมูลกิจกรรมของอิเวนต์)
┃ ┃ ┣ 📜event.csv(เก็บข้อมูลอิเวนต์ทั้งหมด)
┃ ┃ ┣ 📜joinEvent.csv(เก็บข้อมูลการเข้าร่วมอิเวนต์)
┃ ┣ 📂image(เก็บรูปภาพทั้งหมดที่รับจาก User)
┃ ┃ ┣ 📂avatar(รูปภาพโพรไฟล์)
┃ ┃ ┗ 📂event(รูปภาพอิเวนต์)
┃ ┣ 📂team(เก็บข้อมูลประเภท Team)
┃ ┃ ┣ 📜activity.csv(เก็บข้อมูลกิจกรรมของทีม)
┃ ┃ ┣ 📜chat.csv(เก็บข้อมูลประวิติการสนทนา)
┃ ┃ ┣ 📜team.csv(เก็บข้อมูลทีมทั้งหมด)
┃ ┃ ┗ 📜teamMember.csv(เก็บข้อมูลการเข้าร่วมทีม)
┃ ┣ 📜user-guide.pdf(คู่มือการใช้งาน)
┃ ┗ 📜user.csv
┣ 📂src(Source code ของโปรแกรม)
┃ ┣ 📂main
┃ ┃ ┣ 📂java
┃ ┃ ┃ ┣ 📂cs211
┃ ┃ ┃ ┃ ┗ 📂project
┃ ┃ ┃ ┃ ┃ ┣ 📂controllers](ส่วนที่ใช้ทำงานร่วมกันกับหน้า GUI)
┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂components(เป็นส่วนของ Controller ที่มักถูกเรียกใช้บ่อย ๆ)
┃ ┃ ┃ ┃ ┃ ┣ 📂cs211661project(เป็นส่วนที่เก็บตัวรัน Application)
┃ ┃ ┃ ┃ ┃ ┣ 📂models(ส่วนที่เก็บ Model ทั้งหมด)
┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂collections(ส่วนที่เก็บ Collection ทั้งหมด)
┃ ┃ ┃ ┃ ┃ ┣ 📂services(ส่วนที่เก็บ Services ทั้งหมด)
┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂alert(เก็บรูปแบบการแจ้งเตือนทั้งหมด)
┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂comparator(เก็บ Comparator ทั้งหมด)
┃ ┃ ┃ ┃ ┃ ┗ 📜Main.java(ไฟล์ที่เรียกใช้งานตัว Application)
┃ ┃ ┃ ┗ 📜module-info.java
┃ ┃ ┗ 📂resources
┃ ┃ ┃ ┗ 📂cs211
┃ ┃ ┃ ┃ ┗ 📂project
┃ ┃ ┃ ┃ ┃ ┗ 📂views
┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂assets(เก็บรูปภาพ)
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂admin(เก็บรูปภาพผู้จัดทำ)
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂background(เก็บรูปภาพพื้นหลังของโปรแกรม)
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂banner(เก็บรูปภาพ Banner ทั้งหมด)
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂fonts(เก็บฟอนต์ที่ใช้ในระบบ)
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂Icons(เก็บไอคอนของโปรแกรม)
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂logo(เก็บรูปภาพโลโก้ของโปรแกรม)
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂professor(เก็บรูปภาพของอาจารย์ประจำวิชา)
┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂components(ส่วนที่เก็บ fxml ที่มักใช้บ่อย ๆ)
┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂style(เก็บ CSS ของระบบ)
┣ 📜.gitignore
┣ 📜pom.xml
┗ 📜README.md
</pre>

## Conclusion[![](https://raw.githubusercontent.com/aregtech/areg-sdk/master/docs/img/pin.svg)](#introduction)

* ความก้าวหน้าของระบบครั้งที่ 1 - Figma Design,Planning
    * Figma  [(Click)](https://www.figma.com/file/dbblYMWKIM9eNKPoFPo6Cf/Event-Management-System---Java?type=design&node-id=1%3A9&mode=design&t=s6iWfBkc7YrVPZoj-1) - [6510405334 กิตติคุณ บุญต่อยุทธ](https://github.com/prokittikun), [6510405482 ณัฐดนัย พิณะเวศน์](https://github.com/jgogo01), [6510405393 จิรภัทร โควินทวงศ์](https://github.com/jk24jirapat), [6510405431 ญาดา รัตนวราหะ](https://github.com/thankkue)
* ความก้าวหน้าของระบบครั้งที่ 2 - UML Class Diagram, Scence Builder, Model and Collections
  * UML diagram [6510405334 กิตติคุณ บุญต่อยุทธ](https://github.com/prokittikun)
    * controller [(Click)](https://github.com/prokittikun/issue/assets/76927239/2665d630-2c86-4425-81c2-5d41ee66c856)
    * models [(Click)](https://github.com/prokittikun/issue/assets/76927239/7920f416-e4de-4806-b134-8bb33997140f)
    * services [(Click)](https://github.com/prokittikun/issue/assets/76927239/a11cb8f8-43fc-4a6b-bd4f-190352d1608a)
    
  * สร้าง controller, model, service ตาม UML, ขึ้นโครง UI [6510405334 กิตติคุณ บุญต่อยุทธ](https://github.com/prokittikun), [6510405482 ณัฐดนัย พิณะเวศน์](https://github.com/jgogo01), [6510405393 จิรภัทร โควินทวงศ์](https://github.com/jk24jirapat), [6510405431 ญาดา รัตนวราหะ](https://github.com/thankkue)
* ความก้าวหน้าของระบบครั้งที่ 3 - CSV, Integration with CSV
	* ออกแบบ CSV [6510405334 กิตติคุณ บุญต่อยุทธ](https://github.com/prokittikun), [6510405482 ณัฐดนัย พิณะเวศน์](https://github.com/jgogo01)
	* เชื่อมต่อ UI กับ CSV [6510405334 กิตติคุณ บุญต่อยุทธ](https://github.com/prokittikun), [6510405482 ณัฐดนัย พิณะเวศน์](https://github.com/jgogo01), [6510405393 จิรภัทร โควินทวงศ์](https://github.com/jk24jirapat), [6510405431 ญาดา รัตนวราหะ](https://github.com/thankkue)
	* แสดงผลอิเวนต์ การลงทะเบียน [6510405431 ญาดา รัตนวราหะ](https://github.com/thankkue)
	* การจัดการทีม [6510405334 กิตติคุณ บุญต่อยุทธ](https://github.com/prokittikun)
	* การจัดการผู้ใช้งาน [6510405393 จิรภัทร โควินทวงศ์](https://github.com/jk24jirapat)
	* การจัดการอิเวนต์ [6510405482 ณัฐดนัย พิณะเวศน์](https://github.com/jgogo01)
    
* โครงงานที่สมบูรณ์ - ทำ Feature ต่าง ๆ ให้สมบูรณ์ตาม Assignment 
	* เก็บรายละเอียด UI, Mock Up ข้อมูล CSV, จัดทำ Document [6510405431 ญาดา รัตนวราหะ](https://github.com/thankkue)
	* เก็บรายละเอียดในส่วนของโค้ด การแก้บัค ฟีเจอร์ที่ขาดหาย, README.md [6510405334 กิตติคุณ บุญต่อยุทธ](https://github.com/prokittikun)
	* ทำประวัติการเข้าร่วมของอิเวนต์, จัดทำ Document [6510405393 จิรภัทร โควินทวงศ์](https://github.com/jk24jirapat)
	* เก็บรายละเอียดต่าง ๆ ของการจัดการอิเวนต์, จัดทำ Document [6510405482 ณัฐดนัย พิณะเวศน์](https://github.com/jgogo01)
