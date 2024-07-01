// src/router.js
class Router {
    constructor(routes) {
      this.routes = routes;
      this._loadInitialRoute();
    }
  
    loadRoute(...urlSegments) {
      const matchedRoute = this._matchUrlToRoute(urlSegments);
      const url = `/${urlSegments.join('/')}`;
      history.pushState({}, '', url);
      const routerOutlet = document.getElementById('app');
      fetch(matchedRoute.template)
        .then(response => response.text())
        .then(html => {
          routerOutlet.innerHTML = html;
          if (matchedRoute.controller) {
            matchedRoute.controller();
          }
        })
        .catch(error => console.error('Error loading page:', error));
    }
  
    _matchUrlToRoute(urlSegments) {
      return this.routes.find(route => {
        const routePathSegments = route.path.split('/').slice(1);
        if (routePathSegments.length !== urlSegments.length) {
          return false;
        }
  
        return routePathSegments
          .every((routePathSegment, i) => routePathSegment === urlSegments[i]);
      });
    }
  
    _loadInitialRoute() {
      const pathNameSplit = window.location.pathname.split('/');
      const pathSegments = pathNameSplit.length > 1 ? pathNameSplit.slice(1) : '';
      this.loadRoute(...pathSegments);
    }
  }
  
  export default Router;
  