let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");

document.addEventListener( "DOMContentLoaded", () => {
   let studyBoardNo = $("#studyBoardNo").val()
   const calendar = new FullCalendar.Calendar( document.getElementById( "_dm-calendar" ), {
      locale: "ko",
      timeZone: "UTC",
      editable: true,
      droppable: true, // this allows things to be dropped onto the calendar
      dayMaxEvents: true, // allow "more" link when too many events
      headerToolbar: {
         left: "prev,next today",
         center: "title",
         right: "dayGridMonth,timeGridWeek,timeGridDay,listMonth"
      },

      themeSystem: "bootstrap5",

      buttonIcons: {
         close: " demo-psi-cross",
         prev: " demo-psi-arrow-left",
         next: " demo-psi-arrow-right",
         prevYear: " demo-psi-arrow-left-2",
         nextYear: " demo-psi-arrow-right-2"
      },

      events: []
   });

   calendar.render();

   document.getElementById("saveEventButton").addEventListener("click", () => {
      const title = document.getElementById("eventTitle").value;
      const start = document.getElementById("eventStart").value;
      const end = document.getElementById("eventEnd").value;

      if (title && start && end) {
         calendar.addEvent({
            title: title,
            start: start,
            end: end,
            className: 'bg-' + getRandomColorClass()
         });

         fetch('/study/add-event', {
            method: "POST",
            headers: {
               'Content-Type': 'application/json',
               'X-CSRF-TOKEN' : token,
            },
            body: JSON.stringify({
               studyBoardNo: studyBoardNo,
               title: title,
               start: start,
               end: end,
               className: 'bg-' + getRandomColorClass()
            })
         })
             .then(response => response.json())
             .then(data => {
                if (data.status === 'success') {
                   alert(data.message)
                } else {
                   alert("일정 추가에 실패했습니다")
                }
             })
             .catch(error => {
                console.error("Error", error);
                alert(error.message);
             });

         let addEventModal = bootstrap.Modal.getInstance(document.getElementById("addEventModal"));
         addEventModal.hide();

         document.getElementById("addEventForm").reset();
      } else {
         alert("모든 필드를 입력해야 합니다");
      }
   });

   function getRandomColorClass() {
      var colors = ['primary', 'secondary', 'success', 'danger', 'warning', 'info', 'light', 'dark'];
      return colors[Math.floor(Math.random() * colors.length)];
   }

});
