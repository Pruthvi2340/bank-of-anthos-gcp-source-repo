"""API calls"""

from requests import get
from requests.exceptions import RequestException


class ApiRequest:
    """Class for defining an API request"""

    def __init__(self, url, headers, timeout):
        """Initialize an API request"""
        self.url = url
        self.headers = headers
        self.timeout = timeout


class ApiCall:
    """Class for initializing and making an API call"""

    def __init__(self, display_name, api_request, logger):
        """Initialize an API call"""
        self.display_name = display_name
        self.api_request = api_request
        self.logger = logger

    def make_call(self):
        """Making an API call"""
        response = None

        try:
            response = get(url=self.api_request.url,
                           headers=self.api_request.headers,
                           timeout=self.api_request.timeout)
        except (RequestException, ValueError) as err:
            self.logger.error('Error getting %s: %s',
                              self.display_name, str(err))

        return response
