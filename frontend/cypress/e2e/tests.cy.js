describe('ZTP project tests', function() {
    it('Checks if the image in the main page exists: the main page is correct', function() {
        cy.visit('http://localhost:3000');
        cy.get('.row').find("img").should('be.visible');
    });
});

describe('ZTP project tests', function() {
    it('Checks if the menu is correct', function() {
        cy.visit('http://localhost:3000');
        cy.get('#menu').within(() => {
            cy.get('a').should('have.length', 4);

            cy.get('a').eq(0).should('have.text', 'Home').and('have.attr', 'href', '/');
            cy.get('a').eq(1).should('have.text', 'Logout').and('have.attr', 'href', '/');
            cy.get('a').eq(2).should('have.text', 'Register').and('have.attr', 'href', '/register');
            cy.get('a').eq(3).should('have.text', 'Books').and('have.attr', 'href', '/books');
        });
    });
});

describe('ZTP project tests', function() {
    it('Checks if the login form is correct', function() {
        cy.visit('http://localhost:3000');
        cy.get('#loginForm').within(() => {
            cy.get('.col').should('have.length', 3);

            cy.get('.col').eq(0).find('input').should('have.attr', 'type', 'email').and('exist').and('be.visible');
            cy.get('.col').eq(1).find('input').should('have.attr', 'type', 'password').and('exist').and('be.visible');
            cy.get('.col').eq(2).find('button').should('have.attr', 'type', 'submit').and('exist').and('be.visible');
        });
    });
});

describe('ZTP project tests', function() {
    it('Checks if the login form works', function() {
        cy.visit('http://localhost:3000');
        cy.get('#loginForm').within(() => {
            cy.get('.col').eq(0).find('input').type('EMAIL@WP.PL');
            cy.get('.col').eq(1).find('input').type('PASS');
            cy.get('.col').eq(2).find('button').click();

            cy.on('window:alert', (text) => {
                expect(text).to.equal('Successfully logged in!');
            });
        });

        cy.get('#loggedIn').should('exist').and('be.visible').and("have.text", "Currently logged in as EMAIL@WP.PL");
    });
});

describe('ZTP project tests', function() {
    it('Checks if the logout works', function() {
        cy.visit('http://localhost:3000');
        cy.get('#loginForm').within(() => {
            cy.get('.col').eq(0).find('input').type('EMAIL@WP.PL');
            cy.get('.col').eq(1).find('input').type('PASS');
            cy.get('.col').eq(2).find('button').click();
        });

        cy.get('#loggedIn').should('exist').and('be.visible').and("have.text", "Currently logged in as EMAIL@WP.PL");
        cy.get('#menu').within(() => {
            cy.get('a').eq(1).click();

            cy.on('window:alert', (text) => {
                expect(text).to.equal('Successfully logged out!');
            });
        });
    });
});

describe('ZTP project tests', function() {
    it('Checks if register form is visible and correct', function() {
        cy.visit('http://localhost:3000/register');
        cy.get('#registerForm').within(() => {
            cy.get('.col').should('have.length', 4);

            cy.get('.col').eq(0).find('input').should('have.attr', 'type', 'email').and('exist').and('be.visible');
            cy.get('.col').eq(1).find('input').should('have.attr', 'type', 'password').and('exist').and('be.visible');
            cy.get('.col').eq(2).find('input').should('have.attr', 'type', 'password').and('exist').and('be.visible');
            cy.get('.col').eq(3).find('button').should('have.attr', 'type', 'submit').and('exist').and('be.visible');
        });
    });
});

describe('ZTP project tests', function() {
    it('Checks if registering works', function() {
        cy.visit('http://localhost:3000/register');

        cy.get('#registerForm').within(() => {
            cy.get('.col').eq(0).find('input').type('CYPRESS@cy.com');
            cy.get('.col').eq(1).find('input').type('P4SSW0RD!');
            cy.get('.col').eq(2).find('input').type('P4SSW0RD!');
            cy.get('.col').eq(3).find('button').click();
        });

        cy.get('#loginForm').within(() => {
            cy.get('.col').eq(0).find('input').type('CYPRESS@cy.com');
            cy.get('.col').eq(1).find('input').type('P4SSW0RD!');
            cy.get('.col').eq(2).find('button').click();
        });

        cy.get('#loggedIn').should('exist').and('be.visible').and("have.text", "Currently logged in as CYPRESS@cy.com");
    });
});

describe('ZTP project tests', function() {
    it('Checks if registering fails if nonidentical passwords are input', function() {
        cy.visit('http://localhost:3000/register');

        cy.get('#registerForm').within(() => {
            cy.get('.col').eq(0).find('input').type('CYPRESS@cy.com');
            cy.get('.col').eq(1).find('input').type('P4SSW0RD!');
            cy.get('.col').eq(2).find('input').type('different');
            cy.get('.col').eq(3).find('button').click();

            cy.on('window:alert', (text) => {
                expect(text).to.equal("The passwords are not the same! Try again.");
            });
        });
    });
});

describe('ZTP project tests', function() {
    it('Checks if all elements on the book page exist and are correct', function() {
        cy.visit('http://localhost:3000/books');

        cy.get('#filter').should('exist').and('be.visible');
        cy.get('#books').should('exist').and('be.visible');
        cy.get('#pagination').should('exist').and('be.visible');
    });
});

describe('ZTP project tests', function() {
    it('Checks if filtering works', function() {
        cy.visit('http://localhost:3000/books');

        cy.get('#filter').type('The Mysterious Flame Of Queen Loana');
        cy.get('#books').within(() => {
            cy.get('.col').its('length').should('be.lt', 6);
        });
    });
});

describe('ZTP project tests', function() {
    it('Checks if pagination works', function() {
        cy.visit('http://localhost:3000/books');

        cy.get('#pagination').within(() => {
            cy.get('button').eq(1).click();
        });

        cy.get('#books').within(() => {
            cy.get('.col').its('length').should('be.lt', 6);
        });
    });
});