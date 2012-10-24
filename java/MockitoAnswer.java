
	final StringBuffer buff = new StringBuffer();

    JspContext context = mock(JspContext.class);
    JspWriter writer = mock(JspWriter.class);
    Answer answerToBuffer = new Answer<Object>() {
        @Override
        public Object answer(InvocationOnMock invocation) throws Throwable {
            buff.append(String.valueOf(invocation.getArguments()[0]));
            return buff;
        }
    };
    doAnswer(answerToBuffer).when(writer).print(anyString());
