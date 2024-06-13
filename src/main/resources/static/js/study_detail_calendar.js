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

   fetch(`/study/get-events?studyBoardNo=${studyBoardNo}`, {
      method: 'GET',
      headers: {
         'Content-Type': 'application/json',
         'X-CSRF-TOKEN': token,
      }
   })
       .then(response => response.json())
       .then(events => {
          events.forEach(event => {
             calendar.addEvent({
                title: event.title,
                start: event.start,
                end: new Date(new Date(event.end).getTime() + 24 * 60 * 60 * 1000),
                className: event.className
             });
          });
       })
       .catch(error => {
          console.error("Error fetching events:", error);
       });

   document.getElementById("saveEventButton").addEventListener("click", () => {
      const title = document.getElementById("eventTitle").value;
      const start = document.getElementById("eventStart").value;
      const end = document.getElementById("eventEnd").value; // Add one day to end date
      const className = document.getElementById("eventColor").value;

      if (title && start && end) {
         calendar.addEvent({
            title: title,
            start: start,
            end: new Date(new Date(end).getTime() + 24 * 60 * 60 * 1000),
            className: className
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
               className: className
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
});
