var dropdown = document.getElementsByClassName("dropdown-btn");
var i;

for (i = 0; i < dropdown.length; i++) {
    dropdown[i].addEventListener("click", function() {
        this.classList.toggle("active");
        var dropdownContent = this.nextElementSibling;
        if (dropdownContent.style.display === "block") {
            dropdownContent.style.display = "none";
        } else {
            dropdownContent.style.display = "block";
        }
    });
}

const toggleSidebar = () => {

    if ($('.sidebar').is(":visible")) {
        $(".sidebar").css("display", "none");
        $(".content").css("margin-left", "1%");

    } else {
        //false
        //show krna hai
        $(".sidebar").css("display", "block");
        $(".content").css("margin-left", "20%");

    }

};
const search = () => {

    let query = $("#search-input").val();

    if (query == "") {
        $(".search-result").hide();
    } else {
        console.log(query);

        //sending request to server basically calling rest controller that we made
        let url = 'http://localhost:8079/search/' + query;

        fetch(url)
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                //data.....

                let text = '<div class="list-group">';
                data.forEach(query => {
                    text += '<a href = "/user/query/' + query.queryId + '" class="list-group-item list-group-action">' + query.title + '</a>';

                });

                text += '</div>';

                $(".search-result").html(text);
                $(".search-result").show();

            });



    }
};