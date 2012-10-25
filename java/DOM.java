public class DOM {
  
	private void wait_between_tries() {
		try { Thread.sleep(2000);	} catch (InterruptedException e) { 	}
	}	
	private static int NUMBER_OF_TRIES = 2;
	
	private WebDriver driver;
	private WebElement topElement;

	public DOM() {
		this.driver = Steps.driver;
	}
	public DOM(WebElement topElement) {
		this.driver = Steps.driver;
		this.topElement = topElement;
	}

  public WebElement elementContains(String cssSelector, String containingText) {
  	int number_of_tries_left = NUMBER_OF_TRIES;
  	WebElement elementFound = null;
  	while (elementFound == null && number_of_tries_left > 0) {
  		try {
  	  	List<WebElement> elements = elements(By.cssSelector(cssSelector));
  	  	for (WebElement tempElement : elements) {
  	  		String text = tempElement.getText();
  	  		if (text.contains(containingText)) {
  	  			elementFound = tempElement;
  	  			break;
  	  		}
  	  	}
  			if (elementFound == null) {
  				throw new NoSuchElementException(String.valueOf(cssSelector));
  			}
  		} catch (Exception noElement) {
  			System.out.println("Tried:"+String.valueOf(cssSelector));
  			number_of_tries_left--;
  			wait_between_tries();
  		}
  	}
  	return elementFound;
  }
	
  public WebElement element(String cssSelector) {
  	return element(By.cssSelector(cssSelector));
  }
  
	
  /**
   * Automatically retries after waiting for the page to load.
   * @param source
   * @return
   */
  private WebElement element(By source) {
  	WebElement element = null;
  	int number_of_tries_left = NUMBER_OF_TRIES;
  	boolean isElementFound = false;
  	while (!isElementFound && number_of_tries_left > 0) {
  		try {
  			if (topElement != null) {
  				element = topElement.findElement(source);
  			}
  			else {
  				element = driver.findElement(source);  				
  			}
  			if (element == null || (!element.getTagName().equalsIgnoreCase("title") && !element.isDisplayed())) {
  				throw new NoSuchElementException(String.valueOf(source));
  			}
  			isElementFound = true;
  		} catch (Exception noElement) {
  			System.out.println("Tried:"+String.valueOf(source));
  			number_of_tries_left--;
  			wait_between_tries();
  		}
  	}
  	return element;
	}

  public List<WebElement> elements(String cssSelector) {
  	return elements(By.cssSelector(cssSelector));
  }
  
  public List<WebElement> elementsContain(String cssSelector, String containingText) {
  	int number_of_tries_left = NUMBER_OF_TRIES;
  	List<WebElement> elementsFound = new ArrayList();
  	while (elementsFound.size() == 0 && number_of_tries_left > 0) {
  		try {
  	  	List<WebElement> elements = elements(By.cssSelector(cssSelector));
  	  	for (WebElement tempElement : elements) {
  	  		String text = tempElement.getText();
  	  		if (text.contains(containingText)) {
  	  			elementsFound.add(tempElement);
  	  		}
  	  	}
  			if (elementsFound.size() == 0) {
  				throw new NoSuchElementException(String.valueOf(cssSelector));
  			}
  		} catch (Exception noElement) {
  			System.out.println("Tried:"+String.valueOf(cssSelector));
  			number_of_tries_left--;
  			wait_between_tries();
  		}
  	}
  	return elementsFound;
  }
  
  private List<WebElement> elements(By source) {
  	List<WebElement> elements = null;
  	int number_of_tries_left = NUMBER_OF_TRIES;
  	boolean isElementFound = false;
  	while (!isElementFound && number_of_tries_left > 0) {
  		try {
  			if (topElement != null) {
  				elements = topElement.findElements(source);
  			}
  			else {
  				elements = driver.findElements(source);  				
  			}
  			if (elements == null || elements.size() == 0 || !elements.get(0).isDisplayed()) {
  				throw new NoSuchElementException(String.valueOf(source));
  			}
  			isElementFound = true;
  		} catch (Exception noElement) {
  			System.out.println("Tried:"+String.valueOf(source));
  			number_of_tries_left--;
  			wait_between_tries();
  		}
  	}
  	if (elements == null) {
  		elements = new ArrayList<WebElement>();
  	}
  	return elements;
	}
}