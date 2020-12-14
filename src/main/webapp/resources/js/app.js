document.addEventListener("DOMContentLoaded", function () {
    /**
     * Form Select
     */
    let list = [];
    let quantity;
    let fundationName;
    let street;
    let city;
    let zipCode;
    let phone;
    let data;
    let time;
    let more;


    class FormSelect {
        constructor($el) {
            this.$el = $el;
            this.options = [...$el.children];
            this.init();
        }

        init() {
            this.createElements();
            this.addEvents();
            this.$el.parentElement.removeChild(this.$el);
        }

        createElements() {
            // Input for value
            this.valueInput = document.createElement("input");
            this.valueInput.type = "text";
            this.valueInput.name = this.$el.name;

            // Dropdown container
            this.dropdown = document.createElement("div");
            this.dropdown.classList.add("dropdown");

            // List container
            this.ul = document.createElement("ul");

            // All list options
            this.options.forEach((el, i) => {
                const li = document.createElement("li");
                li.dataset.value = el.value;
                li.innerText = el.innerText;

                if (i === 0) {
                    // First clickable option
                    this.current = document.createElement("div");
                    this.current.innerText = el.innerText;
                    this.dropdown.appendChild(this.current);
                    this.valueInput.value = el.value;
                    li.classList.add("selected");
                }

                this.ul.appendChild(li);
            });

            this.dropdown.appendChild(this.ul);
            this.dropdown.appendChild(this.valueInput);
            this.$el.parentElement.appendChild(this.dropdown);
        }

        addEvents() {
            this.dropdown.addEventListener("click", e => {
                const target = e.target;
                this.dropdown.classList.toggle("selecting");

                // Save new value only when clicked on li
                if (target.tagName === "LI") {
                    this.valueInput.value = target.dataset.value;
                    this.current.innerText = target.innerText;
                }
            });
        }
    }

    document.querySelectorAll(".form-group--dropdown select").forEach(el => {
        new FormSelect(el);
    });

    /**
     * Hide elements when clicked on document
     */
    document.addEventListener("click", function (e) {
        const target = e.target;
        const tagName = target.tagName;

        if (target.classList.contains("dropdown")) return false;

        if (tagName === "LI" && target.parentElement.parentElement.classList.contains("dropdown")) {
            return false;
        }

        if (tagName === "DIV" && target.parentElement.classList.contains("dropdown")) {
            return false;
        }

        document.querySelectorAll(".form-group--dropdown .dropdown").forEach(el => {
            el.classList.remove("selecting");
        });
    });

    /**
     * Switching between form steps
     */
    class FormSteps {
        constructor(form) {
            this.$form = form;
            this.$next = form.querySelectorAll(".next-step");
            this.$prev = form.querySelectorAll(".prev-step");
            this.$step = form.querySelector(".form--steps-counter span");
            this.currentStep = 1;
            this.$stepInstructions = form.querySelectorAll(".form--steps-instructions p");
            const $stepForms = form.querySelectorAll("form > div");
            this.slides = [...this.$stepInstructions, ...$stepForms];

            this.init();
        }

        /**
         * Init all methods
         */
        init() {
            this.events();
            this.updateForm();
        }

        /**
         * All events that are happening in form
         */
        events() {
            // Next step
            this.$next.forEach(btn => {
                btn.addEventListener("click", e => {
                    e.preventDefault();

                    if (this.currentStep === 1) {
                        if (list.length > 0) {
                            list = [];
                        }
                        const check = document.querySelectorAll("input[type='checkbox']");
                        check.forEach(el=>{
                            if(el.checked){
                                list.push(el.nextElementSibling.nextElementSibling.textContent);
                            }
                        })
                    }

                    if (this.currentStep === 2) {
                        const quantityFromForm = document.querySelector("input[type='number']");
                        quantity = quantityFromForm.value;
                    }

                    if(this.currentStep === 3){
                        const name = document.querySelectorAll("input[type='radio']");
                        name.forEach(el=>{
                            if(el.checked){
                                fundationName = el.nextElementSibling.nextElementSibling.firstElementChild.textContent.replace("Fundacja","");
                            }
                        })
                    }

                    if(this.currentStep === 4){
                        const street2 = document.querySelector("input[name='street']");
                        const city2 = document.querySelector("input[name='city']");
                        const zipCode2 = document.querySelector("input[name='zipCode']");
                        const phone2 = document.querySelector("input[name='phone']");
                        const data2 = document.querySelector("input[name='pickUpDate']");
                        const hour2 = document.querySelector("input[name='pickUpTime']");
                        const more2 = document.querySelector("#moreComment");
                        street = street2.value;
                        city = city2.value;
                        zipCode = zipCode2.value;
                        phone = phone2.value;
                        data = data2.value;
                        time = hour2.value;
                        more = more2.value;
                    }
                    this.currentStep++;
                    this.updateForm();

                    if (this.currentStep === 5) {
                        const sth = document.querySelector("div.active");
                        //ilosc workow i co oddaje
                        sth.children[1].firstElementChild.children[1].firstElementChild.lastElementChild.textContent = quantity + " worków: " + list.map(el => {
                            return " " + el;
                        });
                        //nazwa findacji
                        sth.children[1].firstElementChild.children[1].lastElementChild.lastElementChild.textContent = "Fundacja: " + fundationName;
                        sth.children[1].lastElementChild.children[0].children[1].children[0].textContent = street;
                        sth.children[1].lastElementChild.children[0].children[1].children[1].textContent = city;
                        sth.children[1].lastElementChild.children[0].children[1].children[2].textContent = zipCode;
                        sth.children[1].lastElementChild.children[0].children[1].children[3].textContent = phone;
                        sth.children[1].lastElementChild.children[1].children[1].children[0].textContent = data;
                        sth.children[1].lastElementChild.children[1].children[1].children[1].textContent = time;
                        sth.children[1].lastElementChild.children[1].children[1].children[2].textContent = more;
                        console.log("PODSUMOWANIE");
                    }
                });
            });

            // Previous step
            this.$prev.forEach(btn => {
                btn.addEventListener("click", e => {
                    e.preventDefault();

                    this.currentStep--;
                    // if(this.currentStep === 1){
                    //   list
                    // }
                    this.updateForm();
                });
            });

            // Form submit
            this.$form.querySelector("form").addEventListener("submit", e => this.submit(e));
        }

        /**
         * Update form front-end
         * Show next or previous section etc.
         */
        updateForm() {
            this.$step.innerText = this.currentStep;

            // TODO: Validation

            this.slides.forEach(slide => {
                slide.classList.remove("active");

                if (slide.dataset.step == this.currentStep) {
                    slide.classList.add("active");
                }
            });

            this.$stepInstructions[0].parentElement.parentElement.hidden = this.currentStep >= 5;
            this.$step.parentElement.hidden = this.currentStep >= 5;

            // TODO: get data from inputs and show them in summary
        }

    }

    const form = document.querySelector(".form--steps");
    if (form !== null) {
        new FormSteps(form);
    }
    let mybutton = document.getElementById("myBtn");

// When the user scrolls down 20px from the top of the document, show the button
    window.onscroll = function() {scrollFunction()};

    function scrollFunction() {
        if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
            mybutton.style.display = "block";
        } else {
            mybutton.style.display = "none";
        }
    }

// When the user clicks on the button, scroll to the top of the document
    function topFunction() {
        document.body.scrollTop = 0; // For Safari
        document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
    }
    mybutton.addEventListener("click",e=>{
        topFunction();
    })
});