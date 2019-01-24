package api.javajuke.tests;

import api.javajuke.data.TrackRepository;
import api.javajuke.data.model.Track;
import api.javajuke.service.TrackService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.Assert;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrackServiceUnitTest {

    @Mock
    private TrackRepository trackRepository;

    @InjectMocks
    private TrackService trackService;

    @Test
    public void testGetTrack() {
        // Create a dummy track
        Track dummyTrack = new Track("/example/path");
        // Return a stub when findById is called
        when(trackRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(dummyTrack));
        // Try to obtain the track
        Track track = trackService.getTrack(1);
        // Check if the path of the track equals "/example/path"
        Assert.assertEquals(track.getPath(), "/example/path");
    }
}