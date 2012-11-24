package net.simpleframework.mvc.component.ui.videoplayer;

import net.simpleframework.common.StringUtils;
import net.simpleframework.mvc.component.AbstractComponentRender.ComponentJavascriptRender;
import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.ComponentRenderUtils;
import net.simpleframework.mvc.component.ComponentUtils;
import net.simpleframework.mvc.component.IComponentRegistry;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class VideoPlayerRender extends ComponentJavascriptRender {

	public VideoPlayerRender(final IComponentRegistry componentRegistry) {
		super(componentRegistry);
	}

	@Override
	public String getJavascriptCode(final ComponentParameter cParameter) {
		final VideoPlayerBean videoPlayer = (VideoPlayerBean) cParameter.componentBean;

		final StringBuilder sb = new StringBuilder();
		sb.append(ComponentRenderUtils.initContainerVar(cParameter));
		final String actionFunc = ComponentRenderUtils.actionFunc(cParameter);
		sb.append(actionFunc).append(".videoPlayer = $f(").append(ComponentRenderUtils.VAR_CONTAINER)
				.append(", {");
		sb.append("buffering: true,");
		sb.append("src: \"");
		sb.append(ComponentUtils.getResourceHomePath(VideoPlayerBean.class)).append(
				"/flash/flowplayer.swf\"");
		sb.append("}, {");
		final String onload = videoPlayer.getJsLoadedCallback();
		if (StringUtils.hasText(onload)) {
			sb.append("onLoad: function() {");
			sb.append(onload);
			sb.append("},");
		}
		sb.append("logo: null,");
		// sb.append(ComponentRenderUtils.jsonAttri(cParameter, "height"));
		// sb.append(ComponentRenderUtils.jsonAttri(cParameter, "width"));
		sb.append("clip: {");
		sb.append("autoPlay: ").append(videoPlayer.isAutoPlay()).append(",");
		sb.append("scaling: \"fit\",");
		String videoUrl = null;
		final IVideoPlayerHandler handle = (IVideoPlayerHandler) cParameter.getComponentHandler();
		if (handle != null) {
			videoUrl = handle.getVideoUrl(cParameter);
		}
		if (!StringUtils.hasText(videoUrl)) {
			videoUrl = videoPlayer.getVideoUrl();
		}
		sb.append("url: \"").append(cParameter.getContextPath()).append(videoUrl).append("\"");
		sb.append("}");
		sb.append("});");
		return ComponentRenderUtils.genActionWrapper(cParameter, sb.toString());
	}
}
